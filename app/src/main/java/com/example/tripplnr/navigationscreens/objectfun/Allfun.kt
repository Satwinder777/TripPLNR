package com.example.tripplnr.navigationscreens.objectfun

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import com.example.tripplnr.navigationscreens.LiveDataVM.Live
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text


object Allfun {

    fun task1(){
        Log.e("testlog", "task1: i am a log", )
        println("testobj")
    }









    fun loginUser(email:EditText,passworde:EditText,context:Context,loginbtn:MaterialButton){
        //    private lateinit var binding1:ActivitySignUpBinding


             var auth:FirebaseAuth = Firebase.auth

            loginbtn.setOnClickListener {
//                val userName = email.text.toString()
//                val password0 = password.text.toString()
//
//                if (userName.isNullOrEmpty().not() && password0.isNullOrEmpty().not() ) {
//                    auth.signInWithEmailAndPassword(userName, password0)
//                        .addOnCompleteListener { task: Task<AuthResult> ->
//
//                                if (task.isSuccessful) {
//                                    Toast.makeText(
//                                        context,
//                                        "You loged successfully",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                } else {
//                                    Toast.makeText(context, "Wrong Password!", Toast.LENGTH_SHORT)
//                                        .show()
//                                    Log.e("tag12", "loginUser: ${task.exception} ", )
//                                }
//
//                        }
//
//
////                    Firebase.auth.signInWithEmailAndPassword(userName, password0)
////                        .addOnCompleteListener { task: Task<AuthResult> ->
////                            if (task.isSuccessful) {
////                                // Login successful
////                                val user: FirebaseUser? = auth.currentUser
////                                // Proceed with further actions
////                                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
////
////                            } else {
////                                // Login failed
////                                val exception = task.exception
////                                // Handle the error
////                                Toast.makeText(context, "$exception", Toast.LENGTH_SHORT).show()
////                                Log.e("TAG12", "loginUser: $exception", )
////
////                            }
////                        }
//                }
//                else{
//                    Toast.makeText(context, "Please Fill Blank!", Toast.LENGTH_SHORT).show()
//                }


                var firebaseAuth = FirebaseAuth.getInstance()


                    val userName = email.text.toString()
                    val password = passworde.text.toString()

                    if (userName.isNullOrEmpty().not() && password.isNullOrEmpty().not()){
                        firebaseAuth.signInWithEmailAndPassword(userName,password).addOnCompleteListener {
                            if (it.isSuccessful){
//                                val intent = Intent(this@SignInActivity,FinalActivity::class.java)
//                                startActivity(intent)
                                Toast.makeText(context, "login!", Toast.LENGTH_SHORT).show()
                                Log.e("TAG12", "loginUser: login  ", )

                            }
                            else{
                                Toast.makeText(context, "Wrong Password!", Toast.LENGTH_SHORT).show()
                                Log.e("TAG12", "loginUser: wrong password  due to: ${it.exception}", )
                            }
                        }
                    }
                    else{
                        Toast.makeText(context, "Please Fill Blank!", Toast.LENGTH_SHORT).show()
                        Log.e("TAG12", "loginUser: Please Fill Blank", )
                    }





            }






    }

    fun textfChar(data:String):List<Char>{

        var words = data.split(" ")
        var fChar = mutableListOf<Char>()

        for (word in words){
            if (word.isNullOrEmpty()){
                fChar.add(word[0])
            }
        }

        return fChar
    }
    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+\$")
        var a =emailRegex.matches(email)
        Log.e("TAG", "isEmailValid: $a", )
        return a
    }
    fun isUserLoggedIn(auth: FirebaseAuth): Boolean {
        val currentUser = auth.currentUser
        return currentUser != null
    }

}
object FirebaseUtils {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
}