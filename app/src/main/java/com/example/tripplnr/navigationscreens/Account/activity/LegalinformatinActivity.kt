package com.example.tripplnr.navigationscreens.Account.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.example.tripplnr.databinding.ActivityLegalinformatinBinding

class LegalinformatinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLegalinformatinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLegalinformatinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backlegalinfo.setOnClickListener {
            onBackPressed()
        }
        binding.termOfUse.setOnClickListener{
            val intent = Intent(this@LegalinformatinActivity,Term_Of_UseActivity::class.java)
            startActivity(intent)
        }
        binding.privacyPolicy.setOnClickListener {
            val intent = Intent(this@LegalinformatinActivity,PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }

    }

}