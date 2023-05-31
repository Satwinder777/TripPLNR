package com.example.tripplnr.navigationscreens.Account

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
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
import com.example.tripplnr.navigationscreens.favorateFragment.FavorateFragment
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.example.tripplnr.navigationscreens.objectfun.FirebaseUtils
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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
//    private var user = Firebase.auth.currentUser
        var auth = Firebase.auth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root


    }

    @SuppressLint("MissingInflatedId", "InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.legalTxt.setOnClickListener {
            val intent = Intent(requireContext(), LegalinformatinActivity::class.java)
            startActivity(intent)
        }

        binding.logintxt.setOnClickListener {

            var view = layoutInflater.inflate(R.layout.login_display, null, false)

            var pop = PopupWindow(
                view,
                ViewGroup.LayoutParams.WRAP_CONTENT,
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

//            Allfun.loginUser(email,password,requireContext(),loginbtn)
            loginbtn.setOnClickListener {




                val userName = email.text.toString().trim()
                val password = password0.text.toString().trim()

                var TAG = "test30"
                auth.createUserWithEmailAndPassword(userName, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            Toast.makeText(requireContext(), "successfully logged", Toast.LENGTH_SHORT).show()
                             var user = auth.currentUser?.displayName
                            if (user != null) {
                                Allfun.textfChar("the sher gill Production").forEach{
                                    Log.e(TAG, "onViewCreated: $user,$it", )
                                }
                            }
                            else{
                                Log.e(TAG, "onViewCreated: null $it", )
                            }
                            pop.dismiss()
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                requireContext(),
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }

            }
            }


            binding.curenncyText.setOnClickListener {
                val intent = Intent(requireContext(), CurrencyActivity::class.java)
                startActivity(intent)
            }
        binding.signOrlogOut.setOnClickListener{
            val user =  auth.currentUser?.displayName

            var crauth = FirebaseAuth.getInstance()
            Log.e("test31", "onViewCreated: user:$user ,,${Allfun.isUserLoggedIn(crauth)} ", )
            auth.signOut()
            Log.e("test31", "onViewCreated: user:$user ,,${Allfun.isUserLoggedIn(crauth)} ", )
            if (it is MaterialButton){
                it.text = "Log in"
            }
R.string.readless


        }
            chipclick(binding.chiKm)
            chipclick(binding.chipMile)
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



}