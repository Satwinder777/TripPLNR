package com.example.tripplnr.navigationscreens.Account

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentAccountBinding
import com.example.tripplnr.navigationscreens.Account.activity.CreateUserActivity
import com.example.tripplnr.navigationscreens.Account.activity.CurrencyActivity
import com.example.tripplnr.navigationscreens.Account.activity.LegalinformatinActivity
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    private lateinit var binding : FragmentAccountBinding
    private lateinit var googleSignInClient: GoogleSignInClient





    //    private var user = Firebase.auth.currentUser
        var auth = Firebase.auth
//    val RC_SIGN_IN = 13



    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(layoutInflater)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        binding.userText.setText("Login")



        Toast.makeText(requireContext(), "${user?.displayName}", Toast.LENGTH_SHORT).show()
        binding.userText.setText(user?.displayName)
        binding.alphaText.visibility = View.VISIBLE

        setcurrency()

        return binding.root


    }

    @SuppressLint("MissingInflatedId", "InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.legalTxt.setOnClickListener {
            val intent = Intent(requireContext(), LegalinformatinActivity::class.java)
            startActivity(intent)
        }


              var gclient =   getGoogleSignInClient(requireContext())
        setcurrency()   // show currency


        binding.logintxt.setOnClickListener {

            var view = layoutInflater.inflate(R.layout.login_display, null, false)
//            checkuserisLogged()
            var pop = PopupWindow(
                view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )

//            pop.contentView = view
            pop.showAtLocation(view, Gravity.CENTER, 0, 0)
            var closebtn = view.findViewById<ImageView>(R.id.closeLogin)
            closebtn.setOnClickListener {
                pop.dismiss()
            }
            var createAcc = view.findViewById<TextView>(R.id.createAccount)
            createAcc.setOnClickListener {
                val intent = Intent(requireContext(), CreateUserActivity::class.java)
                startActivity(intent)
            }
            var email = view.findViewById<EditText>(R.id.emailEditText)
            var password0 = view.findViewById<EditText>(R.id.passwordEditText)
            var loginbtn = view.findViewById<MaterialButton>(R.id.loginMButton)
           var googlelogin =  view.findViewById<MaterialButton>(R.id.btn_login_google)
            googlelogin.setOnClickListener{

                googleloginintent()
                pop.dismiss()
            }

            loginbtn.setOnClickListener {

                val userName = email.text.toString().trim()
                val password = password0.text.toString().trim()

                var TAG = "test30"
                auth.createUserWithEmailAndPassword(userName, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {





                            val user1 = FirebaseAuth.getInstance().currentUser
                            Toast.makeText(requireContext(), "${user1?.displayName}", Toast.LENGTH_SHORT).show()
                             var user = auth.currentUser?.email
                            binding.userText.setText(user)
                            var alphabetfirst = user?.get(0)?.toUpperCase().toString()
                            binding.alphaText.setText(alphabetfirst)
                            binding.alphaText.visibility = View.VISIBLE
                            binding.logoutBtn.visibility = View.VISIBLE
                            binding.logoutBtn.setText("Logout")



                            pop.dismiss()
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                requireContext(),
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            binding.logoutBtn.visibility = View.GONE
                        }
                    }

            }
            }


            binding.curenncyText.setOnClickListener {
                val intent = Intent(requireContext(), CurrencyActivity::class.java)
                startActivity(intent)
            }
        binding.logoutBtn.setOnClickListener{

            if (it is MaterialButton){
                it.text = "Log in"
            }





        }
            chipclick(binding.chiKm)
            chipclick(binding.chipMile)




        binding.logoutBtn.setOnClickListener{
            logout()
            binding.userText.setText("Login")
//            var alphabetfirst = user?.get(0).toString()
            binding.alphaText.visibility = View.INVISIBLE
        }

//        binding.shareapp.setOnClickListener{
////            loginTask()
//
//        }


    }

        private fun chipclick(view: View) {

            var chipkm = binding.chiKm
            var chipMile = binding.chipMile
            view.setOnClickListener {
                when (view) {
                    chipkm -> {
                        chipkm.setChipBackgroundColorResource(R.color.yellow)
                        chipkm.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        chipMile.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.black
                            )
                        )
                        chipMile.setChipBackgroundColorResource(R.color.creame)
                    }
                    chipMile -> {
                        chipMile.setChipBackgroundColorResource(R.color.yellow)
                        chipMile.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                        chipkm.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                        chipkm.setChipBackgroundColorResource(R.color.creame)
                    }
                }
            }
        }



//private fun logout(){
//    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestIdToken(getString(R.string.client_id))
//        .requestEmail()
//        .build()
////    var c = Google_Facebook_login()
////        var context = c.
//    var googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
//
//    val lgText = view?.findViewById<TextView>(R.id.logoutId)
//    lgText?.setOnClickListener {
//        googleSignInClient.signOut()
//        if (googleSignInClient.signOut().isComplete.not()){
//            val intent = Intent(this.requireContext(),Google_Facebook_login::class.java)
//            startActivity(intent)}
//        else{
//            Toast.makeText(this.requireContext(), "something went Wrong", Toast.LENGTH_SHORT).show()
//        }
//    }
//}

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

    @Deprecated("Deprecated in Java")
    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


try {
    if(requestCode ==13 && resultCode==resultCode){
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        val account = task.getResult(ApiException::class.java)
        firebaseAuthWithGoogle(account.idToken!!)



    } else if (resultCode == Activity.RESULT_CANCELED) {
        // Handle canceled sign-in
        Toast.makeText(requireContext(), "Sign-in canceled", Toast.LENGTH_SHORT).show()
        Log.e("resultcancel", "onActivityResult:Sign-in canceled ", )
    }

}
catch (e:Exception){
    Log.e(TAG, "onActivityResult: ${e.message}", )

}


        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in
                val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

                // Get the user's display name (username)
                val username = user?.displayName

                // Use the username as needed
                if (username != null) {
                    // Do something with the username
//                        Log.d("MainActivity", "Username: $username")
                    binding.userText.setText(username)
                    binding.alphaText.visibility = View.VISIBLE
                }else{
                    Log.e("testuser", "onActivityResult: null", )
                }
            } else {
                // Sign in failed
                if (response != null) {
                    // Handle the error
                    val error = response.error
                    Log.e("MainActivity", "Sign-in error: $error")
                }
            }
        }




//        callbackManager.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                // Google Sign-In was successful, authenticate with Firebase.
//                val account = task.getResult(ApiException::class.java)!!
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                // Google Sign-In failed, update UI accordingly.
//                Log.e("statususer", "onActivityResult: ${e.message}", )
//            }
//        }
    }
//    private fun updateUI(user: FirebaseUser?) {
//
//    }
//

//
    private fun firebaseAuthWithGoogle(idToken:String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(requireContext(), "${user?.displayName}", Toast.LENGTH_SHORT).show()
                    binding.userText.setText(user?.displayName)
                    binding.alphaText.visibility = View.VISIBLE
                    // ...
                } else {
                    // Sign in failed, display a message to the user
                    // ...
                    Log.e(TAG, "firebaseAuthWithGoogle: got error ${task.exception}", )
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
            }
    }

//    private fun checkuserisLogged() {
//        val currentUser = auth.currentUser
//
//        if (currentUser != null) {
//            // User is logged in
//            val uid = currentUser.uid
//            val email = currentUser.email
//
//            println("User is logged in")
//            println("UID: $uid")
//            println("Email: $email")
//            Toast.makeText(requireContext(), "$uid,$email", Toast.LENGTH_SHORT).show()
//        } else {
//            // User is not logged in
//            Log.e("abc123", "not present user", )
//        }
//    }
    private fun setcurrency(){
        binding.currencyType.text = Allfun.currencyData.value

    }



    private fun loginTask(){
        val auth = FirebaseAuth.getInstance()
        val providers = listOf(AuthUI.IdpConfig.EmailBuilder().build())
        val signInIntent =
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()


        startActivityForResult(signInIntent, RC_SIGN_IN)
//        auth.currentUser?.displayName
        Log.e("shuda", "loginTask: ${auth.currentUser?.displayName}", )
    }
    private fun logout(){
        googleSignInClient.signOut()
    }
    private fun googleloginintent(){
        startActivityForResult(googleSignInClient.signInIntent  ,13)

    }
}