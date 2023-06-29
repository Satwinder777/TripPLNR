package com.example.tripplnr.navigationscreens.Account.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.tripplnr.R
import com.example.tripplnr.databinding.ActivityCreateUserBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class CreateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateUserBinding
    private lateinit var auth :FirebaseAuth
    private lateinit var first_name_et: EditText
    private lateinit var last_name_et: EditText
    private lateinit var email_id_et: EditText
    private lateinit var password_et: EditText
    private lateinit var confirm_password_et: EditText
    private lateinit var create_account_et: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        first_name_et = binding.firstName
          last_name_et = binding.lastName
          email_id_et = binding.emailId
          password_et = binding.password
         confirm_password_et = binding.confirmPassword
          create_account_et = binding.createAccount


        binding.createAccount.setOnClickListener {
            val name = first_name_et.text.toString().trim()
            val l_name = last_name_et.text.toString().trim()
            val email  = email_id_et.text.toString().trim()
            val password = password_et.text.toString().trim()
            val c_password  = confirm_password_et.text.toString().trim()
            isEmptyView(first_name_et)
            isEmptyView(last_name_et)
            isEmptyView(email_id_et)
            isEmptyView(password_et)
            isEmptyView(confirm_password_et)

            if(email.isNullOrEmpty().not() || password.isNullOrEmpty().not()){
                if (isEmailValid(email)==true){
                    createAccount(name,l_name,email,password,c_password)
                    Log.e("createAccount", "onCreate:successfully data", )                                                  //code logic
                }
                else{
                    email_id_et.setError("please follow proper pattern \"demo@gmail.com\"")
                }
            }
            else{
                if (email.isEmpty() && password.isEmpty()){
                    Log.e("createAccount", "onCreate:email nd pass also emty ", )

                }
                else if (email.isNullOrEmpty()){
                    Log.e("createAccount", "onCreate:email nullemty ", )

                }
                else if (password.isNullOrEmpty()){
                    Log.e("createAccount", "onCreate:pass null  ", )

                }
                else {
                    Log.e("createAccount", "onCreate:password nullemty ", )

                }
                Log.e("createAccount", "onCreate: null occurs ", )
            }
//            first_name_et.text.clear()
//            last_name_et.text.clear()
//            email_id_et.text.clear()
//            password_et.text.clear()
//            confirm_password_et.text.clear()

        }

        binding.backcreateuser.setOnClickListener {
            onBackPressed()
        }
        binding.login.setOnClickListener {
            finish()
        }
    }

    private fun createAccount(firstName: String, lastName: String, email: String?, password: String?, confirmPassword: String){
        var TAG = "createAccount"
     

        if (confirmPassword==password){

                auth.createUserWithEmailAndPassword(email!!,password).addOnCompleteListener { task->
                    var fullname = "$firstName $lastName"
                    if (task.result.user?.displayName.isNullOrEmpty()){
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(fullname)
                            .build()

                        task.result.user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    // Display name updated successfully
                                    // Proceed with your desired logic
                                    Log.e(TAG, "createAccount: namechanged: $fullname", )
                                    finish()
                                } else {
                                    // Error updating display name
                                    // Handle the error
                                    Log.e(TAG, "createAccount: ${task2.exception?.message}", )
                                }
                            }
                    }
                    if(task.isSuccessful){


                        var name = task.result.user?.displayName
                        var email = task.result.user?.email
                        Log.e(TAG, "createAccount: $name,$email", )
                        Toast.makeText(this, "successfully logged!!", Toast.LENGTH_SHORT).show()
                    }else{
                        Log.e(TAG, "createAccount: ${task.exception?.message}", )
                    }
                }
        }
        else{
            confirm_password_et.setError("password does not same")
        }
            }
//    else{
////                Toast.makeText(this, "string null", Toast.LENGTH_SHORT).show()
//                if (email.isNullOrEmpty()&&password.)
//                Log.e(TAG, "createAccount: null data", )
//            }


        


    private fun isEmptyView(view:EditText){
        if (view.text.isNullOrEmpty()){
            view.setError("Enter ${view.hint}")
        }

    }
    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+\$")
        return emailRegex.matches(email)
    }

    }
//}