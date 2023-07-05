package com.example.tripplnr.navigationscreens.Account

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentAccountBinding
import com.example.tripplnr.navigationscreens.Account.activity.*
import com.example.tripplnr.navigationscreens.DataCls.db_firebase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
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
    private lateinit var intent: Intent
    private lateinit var auth: FirebaseAuth
    private lateinit var f_base: FirebaseApp
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var email:EditText
    private lateinit var password0:EditText

    private lateinit var dbRef: DatabaseReference                                                       //db_reference
    private lateinit var db_data_list : MutableList<Any>
    var current_login_Email = ""







    //    private var user = Firebase.auth.currentUser

//    val RC_SIGN_IN = 13

init {
    db_data_list = mutableListOf()
}

//    val user = FirebaseAuth.getInstance().currentUser

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(layoutInflater)
        important_init()
        dbRef = FirebaseDatabase.getInstance().getReference("Users")



        sharedPreferences =  requireContext().getSharedPreferences("AccountFragment_pref", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        auth = Firebase.auth

        if (auth.currentUser!=null){
            val current_user =  auth.currentUser?.email
            WordStartchar("$current_user,123")
            Log.e("loged_user", "onCreateView: auth.currentUser!=null1 :${auth.currentUser?.email}", )


        }
        else{
            Log.e("loged_user", "onCreateView: auth.currentUser==null", )
            auth = Firebase.auth
        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        isUserLoggedIn()
        return binding.root



    }

    @SuppressLint("MissingInflatedId", "InflateParams", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        important_init()
        retrieveUserData()
        binding.legalTxt.setOnClickListener {
            val intent = Intent(requireContext(), LegalinformatinActivity::class.java)
            startActivity(intent)
        }
        binding.shareapp.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain" // Set the MIME type of the content you want to share
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.shareLink)) // Set the text you want to share

            startActivity(Intent.createChooser(shareIntent, "Share via ")) // Display the share dialog to choose an app  // 3 jul

        }
        binding.aboutUs.setOnClickListener {
            var intent = Intent(requireContext(),AboutUsActivity::class.java)
            startActivity(intent)
        }
        binding.becomeAffiliate.setOnClickListener{
            var intent = Intent(requireContext(),BecomeAffiliateActivity::class.java)
            startActivity(intent)
        }
//        binding.legalInformation.setOnClickListener{
//            var intent = Intent(requireContext(),LegalInformationActivity::class.java)
//            startActivity(intent)
//        }
        var imgUrl =  "android.resource://" + requireContext() + "/" + R.drawable.account_ic

//              var gclient =   getGoogleSignInClient(requireContext())
          // show currency


     


        binding.logintxt.setOnClickListener {
            val currentUser = auth.currentUser

            val account = GoogleSignIn.getLastSignedInAccount(requireContext())

            if (account != null || currentUser!=null) {
                // User is logged in with Google
                // You can access the user's information via the account object
                val displayName = account?.displayName
                val email = account?.email

                // Perform actions for a logged-in user
                Toast.makeText(requireContext(), "already logged!", Toast.LENGTH_SHORT).show()

            } else {

                Toast.makeText(requireContext(), "login require!", Toast.LENGTH_SHORT).show()

                var view = layoutInflater.inflate(R.layout.login_display, null, false)

                var pop = PopupWindow(
                    view,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true

                )

                pop.contentView = view

                pop.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.my_background))


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

                  email = view.findViewById<EditText>(R.id.emailEditText)
                 password0 = view.findViewById<EditText>(R.id.passwordEditText)
                var loginbtn = view.findViewById<MaterialButton>(R.id.loginMButton)
                var googlelogin =  view.findViewById<MaterialButton>(R.id.btn_login_google)
                googlelogin.setOnClickListener{

                    googleloginintent()
                    
                    pop.dismiss()
                }

                loginbtn.setOnClickListener {

                    if (email.text.isNullOrEmpty()||password0.text.isNullOrEmpty()){
                        if (email.text.isNullOrEmpty()){
                            email.setError("Please enter your email.")
                        }
                        else{
                            password0.setError("Please enter your password.")
                        }
                    }else{
                        val userName = email.text.toString().trim()
                        val password = password0.text.toString().trim()

                        var TAG = "test30_30"

                        try {
                            auth.signInWithEmailAndPassword(userName,password).addOnCompleteListener { task->
                                if (task.isSuccessful){
                                    var user = auth.currentUser
                                    Log.e("user_data_logged", "onViewCreated: ${user?.displayName},${user?.email}", )
                                    Toast.makeText(requireContext(), "logged!! ${user?.email}", Toast.LENGTH_SHORT).show()



                                    user?.email?.let { it1 -> WordStartchar(it1) }
 
                                    binding.logoutBtn.visibility = View.VISIBLE
                                    binding.logoutBtn.setText("Logout")

                                    var em =  task.result.user?.email

                                    Log.e("completeListner", "onViewCreated: ,$em,", )
                                    task.apply {
                                        editor.putString("loged_user",em)
                                        editor.apply()
                                    }
                                    pop.dismiss()
                                    saveUserData(Pair(user?.email.toString(),user?.displayName.toString()),)
                                    current_login_Email = user?.email.toString()


                                }else{


                                    binding.logoutBtn.visibility = View.GONE
                                    Log.e("user_data_logged_1", "onViewCreated: ${task.exception}", )
                                    Toast.makeText(requireContext(), "exp", Toast.LENGTH_SHORT).show()
                                    when(task.exception.toString()){
                                         "RuntimeExecutionException"->{
                                            password0.setError("wrong password")
                                        }
                                    }
//                                    email.text.clear()
//                                    password0.text.clear()

                                    isemailExist(userName)


                                    val exception = task.exception
                                    if (exception is FirebaseAuthInvalidCredentialsException) {
                                        // Email or password is incorrect
                                        // Handle the error or display an error message
                                        password0.setError("The password is invalid!")
                                    } else {
                                        // Other exceptions occurred
                                        // Handle the error or display an error message
                                    }

                                }
                            }

                        }
                        catch (e:RuntimeExecutionException){
                            password0.setError("Wrong password!")
                            password0.error  ="wrong password"
//                            when(e){
//                                is RuntimeExecutionException ->{
//
//                                }
//                            }
                        }


//                        if (auth.currentUser!=null){
//                            var email = auth.currentUser?.email
//                            var name = auth.currentUser?.displayName
//                            var anony = auth.currentUser?.isAnonymous
//                            Log.e("check2312", "onViewCreated: $email,$name,$anony", )
//                        }
//                        else{
//                            Log.e("check2312", "onViewCreated: usernull", )
//
//                        }
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

            editor.remove("loged_user")
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

                        binding.userText.setText("loginxx")
                        binding.alphaText.visibility = View.GONE

                    }
                    else{
                        user?.displayName?.let { WordStartchar(it) }
                        binding.userText.setText(user?.displayName)
                        binding.alphaText.visibility = View.VISIBLE
                        Log.e("tttttt", "firebaseAuthWithGoogle: ${user?.displayName}", )
                        var loggedUser = user?.displayName

                        saveUserData(Pair(user?.email.toString(),user?.displayName.toString()),true)
                        current_login_Email = user?.email.toString()

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
        auth.signOut()

    }
    private fun googleloginintent(){
        startActivityForResult(googleSignInClient.signInIntent  ,13)

    }

    private fun WordStartchar(line:String):String{

       return if (line.isEmpty()){

            binding.userText.text ="loginW"
            binding.alphaText.visibility = View.GONE
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
//           Log.e("word_setup", "WordStartchar: $line,$userAlphaChar", )
//           editor.putString("loggedUser_1",line)
//           editor.putString("loggedUser_2",userAlphaChar)
//           editor.apply()

           return userAlphaChar
        }


    }
//    fun isInitFireBase(){
//        FirebaseApp.initializeApp(requireContext())
//        if (FirebaseApp.getInstance().)
//    }


    @SuppressLint("SuspiciousIndentation")
    fun isUserLoggedIn(){

        var current_user  = auth.currentUser

        if (current_user != null || GoogleSignIn.getLastSignedInAccount(requireContext())!=null ) {



                if (current_user?.displayName.isNullOrEmpty().not()){
                    var name = current_user?.displayName
                    binding.alphaText.setText(name?.let { WordStartchar(name) })
                    Log.e("shergill", "isUserLoggedIn:isUserLoggedIn() if blick ", )

                }
                else{
                    var email = current_user?.email
                    if (email != null) {
                        WordStartchar(email)
                    }else{
                        Log.e("shergill", "isUserLoggedIn:isUserLoggedIn() ", )
                    }
                }
                binding.alphaText.visibility = View.VISIBLE
                binding.logoutBtn.visibility = View.VISIBLE

        }else{
            binding.alphaText.setText(WordStartchar("login"))
            Log.e("data_currentuserlogg", "isUserLoggedIn:  user not found", )
            binding.alphaText.visibility = View.GONE
            binding.logoutBtn.visibility = View.GONE
            var em = auth.currentUser?.email
            var na = auth.currentUser?.displayName

            Log.e("firebase_user", "isUserLoggedInelse: $em ,$na vfvfvf", )

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
fun isemailExist(email0:String){


    auth.fetchSignInMethodsForEmail(email0)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val signInMethods = task.result?.signInMethods
                if (signInMethods.isNullOrEmpty()) {
                    // Email does not exist
                    // Perform your desired action here
                    email.setError("Wrong email!!")

                } else {
                    // Email already exists
                    // Handle the case accordingly

                }
            } else {
                // Error occurred while checking email existence
                // Handle the error or display an error message
            }
        }


}
    private fun saveUserData(data :Pair<String,String>,signInWithGoogle:Boolean?=false){
        val TAG = "data_base_tag"

        var l_email = data.first
        var l_username = data.second
        val key = dbRef.push().key!!

        var userData_m = db_firebase.UserData(l_email, l_username,signInWithGoogle)
        dbRef.child(key).setValue(userData_m).addOnCompleteListener {
            task->
            var count_same_user = 0
            if (task.isSuccessful){
                Toast.makeText(requireContext(), "added to db", Toast.LENGTH_SHORT).show()
                db_data_list.forEach {
//                    l_email.matches(it.toString())


                    if (it.toString().contains(l_email)){
                        Log.e(TAG, "saveUserData: Already in db => $l_email", )
                        count_same_user+=1
//                        dbRef.child(key).removeValue()
                       if (count_same_user>1){
                           dbRef.child(key).removeValue()
                       }

                    }
                    else{
                        Log.e(TAG, "saveUserData: not found :> $db_data_list", )

                    }
                    Log.e(TAG, "saveUserData: $count_same_user", )
                }

            }
            else{
                Toast.makeText(requireContext(), "error to load data to db", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "saveUserData: ${task.exception?.message} ", )
            }
        }


    }
    private fun retrieveUserData(){
        val TAG = "retrieveUserData"

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                db_data_list.clear()
                if (snapshot.exists()){
                    Log.e(TAG, "onDataChange: ${snapshot.children.count()}", )

                    for (data in snapshot.children){
                        data.value
                        db_data_list.add(data.value!!)

                    }
                }
                Log.e(TAG, "onDataChange: $db_data_list", )
            }

            override fun onCancelled(error: DatabaseError) {

                Log.e(TAG, "onCancelled: ${error.message}", )
            }

        })
        var count = 0
        dbRef.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                if (snapshot.value.toString().contains(current_login_Email)){
                    count+=1
//                    if (count>1){
//                        dbRef.child(snapshot.key!!).removeValue()
//                    }
                }
                Log.e(TAG, "onChildAdded: $snapshot", )
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.e(TAG, "onChildChanged: $snapshot", )
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

                Log.e(TAG, "onChildRemoved: child removed :> $snapshot", )
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                Log.e(TAG, "onChildMoved: $snapshot", )
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "onCancelled: $error", )
            }

        })
    }
}