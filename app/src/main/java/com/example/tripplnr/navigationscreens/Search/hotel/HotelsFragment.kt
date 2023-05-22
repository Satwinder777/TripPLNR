package com.example.tripplnr.navigationscreens.Search.hotel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHotelsBinding
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelTitle
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelchild
import com.example.tripplnr.navigationscreens.Search.adapter.RecyclerAdapterSearchFr

import com.example.tripplnr.navigationscreens.hotelListFragment.HotelListFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HotelsFragment : Fragment() {
    private lateinit var binding :FragmentHotelsBinding
    private lateinit var rc :RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentHotelsBinding.inflate(layoutInflater)
//        val recyclerView = view?.findViewById<RecyclerView>(R.id.searchFrRecycler1)
//        var myAdapter = RecyclerAdapterSearchFr(hotelData())
//        recyclerView?.adapter = myAdapter
       return  binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        rc = binding.searchFrRecycler1
//        GlobalScope.launch {


             rc = binding.searchFrRecycler1
            rc.layoutManager = LinearLayoutManager(requireContext())

            val adapter = RecyclerAdapterSearchFr(hotelData())
            rc.adapter = adapter
            adapter.notifyDataSetChanged()
//        }

        GlobalScope.launch {
            bottomTask(binding.linearDate)
            bottomTask(binding.linearGuest)
        }

        val search = binding.searhhotel
        search.setOnClickListener{
            val newFragment = HotelListFragment()
//            val targetFragment = TargetFragment()
            val fragmentManager = requireParentFragment().parentFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
//        GlobalScope.launch {
//            var hotel = binding.hotelChip
//            var blog = binding.blogChip
//            changeScreen(hotel)
//            changeScreen(blog)
//        }

    }
    fun hotelData():List<hotelTitle>{
        val hotelchildData  = listOf<hotelchild>(
            hotelchild(R.drawable.explore2,"Taj Hotel","Amritsar","3.3"),
            hotelchild(R.drawable.exploreimg,"The Bill Gates","America,Us","4.5"),
            hotelchild(R.drawable.explore2,"Punjab Hotel","Amritsar,Punjab","5.9"),
            hotelchild(R.drawable.exploreimg,"Chandighar Hotel","Chandighar, India","4.7"),
            hotelchild(R.drawable.explore2,"Us Hotel","Us,Amercia","4.8"),
        )
        var list  = listOf<hotelTitle>(
            hotelTitle("Top Hotel",hotelchildData),
            hotelTitle("Best Hotel",hotelchildData),
            hotelTitle("Old Hotel",hotelchildData),
            hotelTitle("Gold Hotel",hotelchildData),

            )
        return list
    }
    @SuppressLint("InflateParams", "MissingInflatedId")
    private fun bottomTask(view: View){

        var selectdate = binding.linearDate
        var selectGuest = binding.linearGuest
        view.setOnClickListener {
            when(view){
                selectdate->{
                    var bottomSelectDate = BottomSheetDialog(requireContext())
                    var view1 = layoutInflater.inflate(R.layout.bottomsheet_selectdate,null,false)
                    bottomSelectDate.setContentView(view1)
                    bottomSelectDate.show()
                    var closebtn = view1.findViewById<MaterialCardView>(R.id.close_selectDate)
                    closebtn.setOnClickListener {
                        bottomSelectDate.dismiss()
                    }
                } selectGuest->{
                var bottomSelectGuest = BottomSheetDialog(requireContext())
                var view1 = layoutInflater.inflate(R.layout.bottom_sheet_gestlist,null,false)
                bottomSelectGuest.setContentView(view1)
                bottomSelectGuest.show()
                var closebtn = view1.findViewById<MaterialCardView>(R.id.close_selectGuest)
                closebtn.setOnClickListener {
                    bottomSelectGuest.dismiss()
                }
            }
                else->{ Toast.makeText(requireContext(), "differentview", Toast.LENGTH_SHORT).show()} }


        }
    }
//    private fun changeScreen(view: View){
//        view.setOnClickListener {
////            var hotel = binding.hotelChip
////            var blog = binding.blogChip
//            when(view){
//                blog ->{
//                    blog.chipBackgroundColor = (ContextCompat.getColorStateList(requireContext(),R.color.white))
//
////                    it.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
//                    blog.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
//                    hotel.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
//
////                    binding.hoteltxt.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
//                    hotel.chipBackgroundColor = (ContextCompat.getColorStateList(requireContext(),R.color.light_yellow))
//
////                    hotel.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.light_yellow))
//
//
//
//
//                }
//                hotel ->{
//                    hotel.chipBackgroundColor = (ContextCompat.getColorStateList(requireContext(),R.color.white))
//
////                    binding.hoteltxt.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
//
////                    binding.blogtxt.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
//
//                    blog.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
//                    hotel.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
//
//                    blog.chipBackgroundColor = (ContextCompat.getColorStateList(requireContext(),R.color.light_yellow))
//
//
//
//
//
//
//                }
//
//            }
//        }
//
//
//    }

}