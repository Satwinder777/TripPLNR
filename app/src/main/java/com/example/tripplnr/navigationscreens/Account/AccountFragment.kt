package com.example.tripplnr.navigationscreens.Account

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentAccountBinding
import com.example.tripplnr.navigationscreens.Account.activity.CreateUserActivity
import com.example.tripplnr.navigationscreens.Account.activity.LegalinformatinActivity

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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root


    }

    @SuppressLint("MissingInflatedId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.legalTxt.setOnClickListener {
            val intent = Intent(requireContext(),LegalinformatinActivity::class.java)
            startActivity(intent)
        }

        binding.logintxt.setOnClickListener {

            var view = layoutInflater.inflate(R.layout.login_display,null,false)

            var pop = PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true )

//            pop.contentView = view
            pop.showAtLocation(view,Gravity.CENTER,0,0)
            var closebtn = view.findViewById<ImageView>(R.id.closeLogin)
            closebtn.setOnClickListener {
                pop.dismiss()
            }
           var createAcc =  view.findViewById<TextView>(R.id.createAccount)
            createAcc.setOnClickListener {
                val intent = Intent(requireContext(), CreateUserActivity::class.java)
                startActivity(intent)
            }

        }
    }

}