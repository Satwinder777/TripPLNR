package com.example.tripplnr.navigationscreens.Account.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tripplnr.R
import com.example.tripplnr.databinding.ActivityCreateUserBinding

class CreateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backcreateuser.setOnClickListener {
            onBackPressed()
        }
    }
}