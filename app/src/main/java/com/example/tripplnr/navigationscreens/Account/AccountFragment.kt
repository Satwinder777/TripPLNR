package com.example.tripplnr.navigationscreens.Account

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentAccountBinding
import com.example.tripplnr.navigationscreens.Account.activity.CreateUserActivity
import com.example.tripplnr.navigationscreens.Account.activity.CurrencyActivity
import com.example.tripplnr.navigationscreens.Account.activity.LegalinformatinActivity
import com.example.tripplnr.navigationscreens.DataCls.User1
import com.example.tripplnr.navigationscreens.DataCls.userData
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.User
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.lang.Character.toUpperCase

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
    private lateinit var intent: Intent
    private lateinit var auth: FirebaseAuth
    private lateinit var f_base: FirebaseApp
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor








    //    private var user = Firebase.auth.currentUser

//    val RC_SIGN_IN = 13



//    val user = FirebaseAuth.getInstance().currentUser

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(layoutInflater)
        important_init()


        sharedPreferences =  requireContext().getSharedPreferences("AccountFragment_pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
//        val currentUser = auth.currentUser
//
//
//
//        if (currentUser?.isAnonymous == true) {
//            // User is anonymous
//            // Perform the necessary actions for anonymous users
//            // For example, show a different UI or restrict certain functionalities
//            Toast.makeText(requireContext(), "anonymous", Toast.LENGTH_SHORT).show()
//        } else {
//            // User is not anonymous
//            // Perform the necessary actions for authenticated users
//            // For example, allow access to restricted features or show personalized content
//            var name = currentUser?.displayName
//            var email = currentUser?.email
//            Toast.makeText(requireContext(), "not anonymous", Toast.LENGTH_SHORT).show()
//            Log.e("c123", "onCreateView: $name,$email", )
//            if (email != null) {
//                WordStartchar(email)
//            }
//
//        }



//        Toast.makeText(requireContext(), "${user?.displayName}", Toast.LENGTH_SHORT).show()



        isUserLoggedIn()
        return binding.root



    }

    @SuppressLint("MissingInflatedId", "InflateParams", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        important_init()
        binding.legalTxt.setOnClickListener {
            val intent = Intent(requireContext(), LegalinformatinActivity::class.java)
            startActivity(intent)
        }
        var imgUrl =  "android.resource://" + requireContext() + "/" + R.drawable.account_ic

//              var gclient =   getGoogleSignInClient(requireContext())
          // show currency


     


        binding.logintxt.setOnClickListener {
            val currentUser = FirebaseAuth.getInstance().currentUser

            val account = GoogleSignIn.getLastSignedInAccount(requireContext())

            if (account != null && currentUser!=null) {
                // User is logged in with Google
                // You can access the user's information via the account object
                val displayName = account.displayName
                val email = account.email
                // Perform actions for a logged-in user
                Toast.makeText(requireContext(), "already logged!", Toast.LENGTH_SHORT).show()
            } else {
                // User is not logged in with Google
                // Perform actions for a logged-out user
                Toast.makeText(requireContext(), "login require!", Toast.LENGTH_SHORT).show()

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
                }                                               //close btn

                var createAcc = view.findViewById<TextView>(R.id.createAccount)
                createAcc.setOnClickListener {
                    val intent = Intent(requireContext(), CreateUserActivity::class.java)
                    startActivity(intent)
                }                                               //create acc

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
//                                binding.alphaText.visibility = View.VISIBLE
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
                    if (auth.currentUser!=null){
                       var email = auth.currentUser?.email
                       var name = auth.currentUser?.displayName
                       var anony = auth.currentUser?.isAnonymous
                        Log.e("check2312", "onViewCreated: $email,$name,$anony", )
                    }
                    else{
                        Log.e("check2312", "onViewCreated: usernull", )

                    }

                }

            
            }
//            val currentUser = FirebaseAuth.getInstance().currentUser
//            if (currentUser != null) {
//                Toast.makeText(requireContext(), "user logged!", Toast.LENGTH_SHORT).show()
//
//            } else {
//                // User is not logged in
//                // Perform actions for a logged-out user
//
//
//            }


        }

       var currencyType = sharedPreferences.getString("currency","USD")
        binding.currencyType.setText(currencyType)



        intent = Intent(requireContext(), CurrencyActivity::class.java)                                         //current activity

            binding.curenncyText.setOnClickListener {

//                setcurrency()
                val requestCode = 1
                startActivityForResult(intent, requestCode)

            }
        binding.logoutBtn.setOnClickListener{
                binding.alphaText.visibility = View.GONE
                it.visibility = View.GONE
                logout()
                binding.userText.setText("Login")
        }
            chipclick(binding.chiKm)
            chipclick(binding.chipMile)






//        binding.shareapp.setOnClickListener{
////            loginTask()
//
//        }
        var chipkm = binding.chiKm
        var chipMile = binding.chipMile
        val enable_unit = sharedPreferences.getString("units","chipMile")
        Log.e("enable_unit", "onViewCreated: $enable_unit", )
        when(enable_unit){
             "KMS"->{
                chipActive(chipkm)
                chipActive_not(chipMile)

            }
            "MILES"->{
                chipActive(chipMile)
                chipActive_not(chipkm)


            }
            else->{
                Toast.makeText(requireContext(), "wrong chip [${enable_unit.toString()}]", Toast.LENGTH_SHORT).show()
            }
        }



        isUserLoggedIn()

    }

        private fun chipclick(view: View){

            var chipkm = binding.chiKm
            var chipMile = binding.chipMile
            var idea :String =""
            view.setOnClickListener {
                 when (view) {
                    chipkm -> {
                        chipActive(chipkm)
                        chipActive_not(chipMile)

                        idea = chipkm.text.toString().toUpperCase()
                        editor.remove("units")
                        editor.putString("units",idea)
                        editor.apply()

                    }
                    chipMile -> {
                        chipActive(chipMile)
                        chipActive_not(chipkm)

                        idea = chipMile.text.toString().toUpperCase()
                        editor.remove("units")
                        editor.putString("units",idea)
                        editor.apply()
                        Log.e("ides_data", "chipclick: $idea", )

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



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)                                                                           ///activity result

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
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {                                                                     /// setting currency 
            val value = data?.getStringExtra("key") // Replace "key" with the key you used in the child activity
            // Use the retrieved value as needed
            editor.putString("currency",value)
            editor.apply()
            Toast.makeText(requireContext(), "$value", Toast.LENGTH_SHORT).show()
            Log.e("valuedata", "onActivityResult: $value", )
            binding.currencyType.setText(value)



        }

//        val username = GoogleSignIn.getLastSignedInAccount(requireContext())
//
//        if (requestCode == RC_SIGN_IN) {
//            val response = IdpResponse.fromResultIntent(data)
//
//            if (resultCode == Activity.RESULT_OK) {
//                // User successfully signed in
//
//                // Get the user's display name (username)
//
//
//                // Use the username as needed
//                if (username != null) {
//                    // Do something with the username
////                        Log.d("MainActivity", "Username: $username")
//                    Log.e(TAG, "onActivityResult: $username", )
////                    binding.userText.setText(username.givenName)
////                    WordStartchar(username.givenName)
//
//
//                    binding.alphaText.visibility = View.VISIBLE
//                    Log.e("username", "onActivityResult:$username ", )
//                }else{
//                    Log.e("testuser", "onActivityResult: null", )
//                }
//            } else {
//                // Sign in failed
//                if (response != null) {
//                    // Handle the error
//                    val error = response.error
//                    Log.e("MainActivity", "Sign-in error: $error")
//                }
//            }
//        }





    }

    @SuppressLint("CommitPrefEdits")
    private fun firebaseAuthWithGoogle(idToken:String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(requireContext(), "${user?.displayName}", Toast.LENGTH_SHORT).show()

                    if(user?.displayName.isNullOrEmpty()){

                        binding.userText.setText(WordStartchar("login"))
                    }
                    else{
                        user?.displayName?.let { WordStartchar(it) }
                        binding.userText.setText(user?.displayName)
                        binding.alphaText.visibility = View.VISIBLE
                        Log.e("tttttt", "firebaseAuthWithGoogle: ${user?.displayName}", )
                        var loggedUser = user?.displayName
                        editor.putString("loggedUser",loggedUser)
                        editor.apply()
                    }
                    // ...
                } else {
                    // Sign in failed, display a message to the user
                    // ...
                    Log.e(TAG, "firebaseAuthWithGoogle: got error ${task.exception}", )
                }
                binding.logoutBtn.visibility = View.VISIBLE
            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
            }
    }





    private fun logout(){
        googleSignInClient.signOut()
    }
    private fun googleloginintent(){
        startActivityForResult(googleSignInClient.signInIntent  ,13)

    }

    private fun WordStartchar(line:String):String{

       return if (line.isNullOrEmpty()){

            binding.userText.text ="login"
            binding.alphaText.visibility = View.INVISIBLE
        return "l"
        }
        else{
            val words = line.split(" ")
            val firstLetters = words.map { it.first() }
            var first = firstLetters.get(0).toUpperCase()
//            when(firstLetters.size){
//                1->{}
//                2->{}
//            }
            var userAlphaChar=""
            if (firstLetters.size>1){
                var second = firstLetters.get(1).toUpperCase()
                userAlphaChar = "$first$second"
            }
            else{
               userAlphaChar = "$first"
            }

//            Log.e("sarra123", "$userAlphaChar", )
            binding.alphaText.text = userAlphaChar
            binding.userText.text = line
            binding.alphaText.visibility = View.VISIBLE

           return userAlphaChar
        }


    }
//    fun isInitFireBase(){
//        FirebaseApp.initializeApp(requireContext())
//        if (FirebaseApp.getInstance().)
//    }


    fun isUserLoggedIn(){


        var current_user  = auth.currentUser
        if (auth.currentUser != null && GoogleSignIn.getLastSignedInAccount(requireContext())!=null ) {

           var n = current_user?.displayName
           var a = current_user?.isAnonymous
          var e =  current_user?.email
            Log.e("data_currentuserlogg", "isUserLoggedIn: $n,$a,$e", )
            binding.alphaText.visibility = View.VISIBLE
            binding.logoutBtn.visibility = View.VISIBLE
            var g_value = sharedPreferences.getString("loggedUser","Login")

            binding.alphaText.setText(WordStartchar(g_value!!))
        }else{
            Log.e("data_currentuserlogg", "isUserLoggedIn:  user not found", )
            binding.alphaText.visibility = View.GONE
            binding.logoutBtn.visibility = View.GONE

        }
    }
 fun important_init(){
     if (FirebaseApp.getApps(requireContext()).isEmpty()){
         val options = FirebaseOptions.Builder()
             .setApiKey(getString(R.string.firebase_key))
             .setApplicationId(getString(R.string.firebase_project_id))
             .setProjectId(getString(R.string.firebase_project_id))
             .build()

         f_base = FirebaseApp.initializeApp(requireContext(), options)
     }

 }
    private fun chipActive(view: Chip){
        view.setChipBackgroundColorResource(R.color.yellow)
        view.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))


    }
    private fun chipActive_not(view: Chip){
        view.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.black
            )
        )
        view.setChipBackgroundColorResource(R.color.creame)


    }
}