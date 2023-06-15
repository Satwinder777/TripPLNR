package com.example.tripplnr.navigationscreens.hotelListFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHotelListBinding
import com.example.tripplnr.navigationscreens.DataCls.guestdatacls
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelListClass
import com.example.tripplnr.navigationscreens.Search.hotel.activity.HotelList2Activity
import com.example.tripplnr.navigationscreens.hotelListFragment.adapter.Hotel_list_recyclerAdapter
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HotelListFragment : Fragment(), Hotel_list_recyclerAdapter.viewdetail {
    private lateinit var binding: FragmentHotelListBinding
    private lateinit var rc: RecyclerView
    private lateinit var query :String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHotelListBinding.inflate(layoutInflater)
//        var data = arguments?.getParcelable("guest", ) ?: ""

//        var myParcelable = arguments?.getParcelable("myParcelable") as MyParcelable
//        val data  = arguments?.getParcelable<guestdatacls>("guest")
//            ?: throw IllegalArgumentException("myParcelable not found in arguments bundle")
        val data = Allfun.guestLiveData.value
        binding.roomtextviewHotelList.setText("${data?.rooms}")
        binding.guestTexthotelList.setText("${ data?.guest }")
//        var date = arguments?.getString("date","default" ) ?: ""
         query = arguments?.getString("query","default" ) ?: ""
//        Log.e("data12", "onCreateView: $data,$date", )
        binding.dateTextviewHotelList.setText(Allfun.dateLiveData.value)
        binding.queryTextViewHotelList.setText(query)

        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rc = binding.hotelListFragmentRecyclerView
        GlobalScope.launch {
            var adapter  = Hotel_list_recyclerAdapter(postData(),this@HotelListFragment)
            rc.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        var backBtn = binding.backHotelList
        backBtn.setOnClickListener {
            //pop back
            fragmentManager?.popBackStack()
//            Log.e("test12", "onViewCreated: ${fragmentManager?.backStackEntryCount} ")

        }
        binding.mapChip.setOnClickListener {
            val intent = Intent(requireContext(),HotelList2Activity::class.java)
            intent.putExtra("query",query)
            startActivity(intent)
        }
        doTask(binding.filterList)
        doTask(binding.sortList)
        binding.editDAtaIV.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }
    private fun postData():List<hotelListClass>{
        var list = listOf<hotelListClass>(
            hotelListClass(R.drawable.exploreimg,"Taj Hotel","Punjab ,India"),
            hotelListClass(R.drawable.exploreimg,"The Royal Villa Hotel","Himachal ,India"),
            hotelListClass(R.drawable.exploreimg,"The Gold Hotel","Pathankot ,India"),
            hotelListClass(R.drawable.exploreimg,"Punjab Hotel","Pakistan ,India"),
        )
        return list

    }

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    fun doTask(view: View){
        var filter = binding.filterList
        var sort = binding.sortList
        view.setOnClickListener {
        val bottom = BottomSheetDialog(requireContext())
            when(view){
                filter->{
                    var view = layoutInflater.inflate(R.layout.filter_bottom_sheet,null,false)
                    bottom.setContentView(view)
                    bottom.show()

                    var closebtn =  view.findViewById<AppCompatImageView>(R.id.closebottomFilter)
                    closebtn.setOnClickListener{
                        bottom.dismiss()
                    }

                    dotask(bottom)
                }
                sort->{

                    var view = layoutInflater.inflate(R.layout.sort_dialog,null,false)
                    bottom.setContentView(view)
                    bottom.show()

                    var closebtn =  view.findViewById<ImageButton>(R.id.closeSort)
                    closebtn.setOnClickListener{
                        bottom.dismiss()
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    private fun dotask(bottom: BottomSheetDialog) {

        var btn1 =  bottom.findViewById<MaterialButton>(R.id.btn1)
        var btn2 =  bottom.findViewById<MaterialButton>(R.id.btn2)
        var btn3 =  bottom.findViewById<MaterialButton>(R.id.btn3)
        var btn4 =  bottom.findViewById<MaterialButton>(R.id.btn4)
        var btn5 =  bottom.findViewById<MaterialButton>(R.id.btn5)


//        var btn1 =  requireView().findViewById<MaterialCardView>(R.id.btn1)
//        var btn2 =  requireView().findViewById<MaterialCardView>(R.id.btn2)
//        var btn3 =  requireView().findViewById<MaterialCardView>(R.id.btn3)
//        var btn4 =  requireView().findViewById<MaterialCardView>(R.id.btn4)
//        var btn5 =  requireView().findViewById<MaterialCardView>(R.id.btn5)
        val list = listOf(btn1,btn2,btn3,btn4,btn5)



        for (button in list) {
            button?.setOnClickListener {
                // Reset background for all buttons
                for (b in list) {
                    b?.setBackgroundResource(R.drawable.card_notchecked)
                    b?.backgroundTintMode = PorterDuff.Mode.MULTIPLY
                    val color = ContextCompat.getColor(requireContext(), R.color.white)
//                materialButton.backgroundTintList = ColorStateList.valueOf(color)
                    b?.backgroundTintList = ColorStateList.valueOf(color)
//                    val color = Color.parseColor("#FF0000") // Replace with your desired color
//                    val colorStateList = ColorStateList.valueOf(color)
//                    b?.backgroundTintList = colorStateList
                }

                // Change background of the clicked button
                button.setBackgroundResource(R.drawable.card_shape)
                val color = ContextCompat.getColor(requireContext(), R.color.yellow)
//                materialButton.backgroundTintList = ColorStateList.valueOf(color)
                button.backgroundTintList = ColorStateList.valueOf(color)


//                materialButton.backgroundTintMode = PorterDuff.Mode.MULTIPLY
                // Perform actions specific to the selected button
                when (button) {
                    btn1 -> {
                        // Actions for button1
//                        it.setBackgroundResource(R.drawable.card_shape)
                        Toast.makeText(requireContext(), "btn1", Toast.LENGTH_SHORT).show()
                    }
                    btn2 -> {
                        // Actions for button2
//                        it.setBackgroundResource(R.drawable.card_shape)
                        Toast.makeText(requireContext(), "btn2", Toast.LENGTH_SHORT).show()

                    }
                    btn3 -> {
                        // Actions for button3
//                        it.setBackgroundResource(R.drawable.card_shape)
                        Toast.makeText(requireContext(), "btn3", Toast.LENGTH_SHORT).show()

                    }
                    btn4 -> {
                        // Actions for button3
//                        it.setBackgroundResource(R.drawable.card_shape)
                        Toast.makeText(requireContext(), "btn4", Toast.LENGTH_SHORT).show()

                    }
                    btn5 -> {
                        // Actions for button3
//                        it.setBackgroundResource(R.drawable.card_shape)
                        Toast.makeText(requireContext(), "btn5", Toast.LENGTH_SHORT).show()

                    }

                }
            }
        }

    }

    override fun checkDetails(view: View, position: Int) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse( "https://www.booking.com"))
        requireContext().startActivity(intent)
    }


}