package com.example.tripplnr.navigationscreens.hotelListFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHotelListBinding
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelListClass
import com.example.tripplnr.navigationscreens.Search.hotel.activity.HotelList2Activity
import com.example.tripplnr.navigationscreens.hotelListFragment.adapter.Hotel_list_recyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HotelListFragment : Fragment() {
    private lateinit var binding: FragmentHotelListBinding
    private lateinit var rc: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHotelListBinding.inflate(layoutInflater)
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rc = binding.hotelListFragmentRecyclerView
        GlobalScope.launch {
            var adapter  = Hotel_list_recyclerAdapter(postData())
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
            startActivity(intent)
        }
        doTask(binding.filterList)
        doTask(binding.sortList)

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

    @SuppressLint("MissingInflatedId")
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


}