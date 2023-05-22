package com.example.tripplnr.navigationscreens.Account.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tripplnr.R
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
    }
}