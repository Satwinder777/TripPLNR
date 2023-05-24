package com.example.tripplnr.navigationscreens.Search.hotel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
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
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HotelsFragment : Fragment() {
    private lateinit var binding: FragmentHotelsBinding
    private lateinit var rc: RecyclerView
    var roomc:Int = 0
    var adultc:Int = 0
    var childc:Int = 0




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHotelsBinding.inflate(layoutInflater)
//        val recyclerView = view?.findViewById<RecyclerView>(R.id.searchFrRecycler1)
//        var myAdapter = RecyclerAdapterSearchFr(hotelData())
//        recyclerView?.adapter = myAdapter
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged", "InflateParams")
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

//            var bottomSelectGuest = BottomSheetDialog(requireContext())
//            var view1 = layoutInflater.inflate(R.layout.bottom_sheet_gestlist,null,false)
//            bottomSelectGuest.setContentView(view1)
//            var item1 = bottomSelectGuest.findViewById<ImageView>(R.id.roomaddbtn)
//            item1?.setOnClickListener {
//                guestDataHandler(item1)
//                Log.e("test24", "bottomTask: clicked", )
//            }
        }

        val search = binding.searhhotel
        search.setOnClickListener {
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

    fun hotelData(): List<hotelTitle> {
        val hotelchildData = listOf<hotelchild>(
            hotelchild(R.drawable.explore2, "Taj Hotel", "Amritsar", "3.3"),
            hotelchild(R.drawable.exploreimg, "The Bill Gates", "America,Us", "4.5"),
            hotelchild(R.drawable.explore2, "Punjab Hotel", "Amritsar,Punjab", "5.9"),
            hotelchild(R.drawable.exploreimg, "Chandighar Hotel", "Chandighar, India", "4.7"),
            hotelchild(R.drawable.explore2, "Us Hotel", "Us,Amercia", "4.8"),
        )
        var list = listOf<hotelTitle>(
            hotelTitle("Top Hotel", hotelchildData),
            hotelTitle("Best Hotel", hotelchildData),
            hotelTitle("Old Hotel", hotelchildData),
            hotelTitle("Gold Hotel", hotelchildData),

            )
        return list
    }

    @SuppressLint("InflateParams", "MissingInflatedId")
    private fun bottomTask(view: View) {

        var selectdate = binding.linearDate
        var selectGuest = binding.linearGuest
        var dayOfMonth0 = 0
        var month0 = 0
        var year0 = 0
        view.setOnClickListener {
            when (view) {
                selectdate -> {
                    var bottomSelectDate = BottomSheetDialog(requireContext())
                    var view1 = layoutInflater.inflate(R.layout.bottomsheet_selectdate, null, false)
                    bottomSelectDate.setContentView(view1)
                    bottomSelectDate.show()
                    var closebtn = view1.findViewById<MaterialCardView>(R.id.close_selectDate)
                    closebtn.setOnClickListener {
                        bottomSelectDate.dismiss()
                    }

                    var calendarView = view1.findViewById<CalendarView>(R.id.calendarView)
                    calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                        // Handle the selected date
                        // year: Int - Selected year
                        // month: Int - Selected month (0-based, i.e., January is 0)
                        // dayOfMonth: Int - Selected day of the month

                        dayOfMonth0 = dayOfMonth
                        month0 = month + 1
                        year0 = year

                    }

                    var selectBtn = view1.findViewById<MaterialButton>(R.id.selectCalenderData)
                    selectBtn.setOnClickListener {
                        Toast.makeText(
                            requireContext(),
                            "the day :$dayOfMonth0, $month0 , $year0",
                            Toast.LENGTH_SHORT
                        ).show()
                        bottomSelectDate.dismiss()
                    }
                }


                selectGuest -> {

                    var bottomSelectGuest = BottomSheetDialog(requireContext())
                    var view1 = layoutInflater.inflate(R.layout.bottom_sheet_gestlist, null, false)
                    bottomSelectGuest.setContentView(view1)
                    bottomSelectGuest.show()
                    var closebtn = view1.findViewById<MaterialCardView>(R.id.close_selectGuest)
                    closebtn.setOnClickListener {
                        bottomSelectGuest.dismiss()
                    }

                    guestDataHandler(bottomSelectGuest)

                }
                else -> {
                    Toast.makeText(requireContext(), "differentview", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    private fun guestDataHandler(view: BottomSheetDialog?) {


        var addroom = view?.findViewById<ImageView>(R.id.roomaddbtn)
        var addadult = view?.findViewById<ImageView>(R.id.adultaddbtn)
        var addchild = view?.findViewById<ImageView>(R.id.childrenaddbtn)

        var roomsub = view?.findViewById<ImageView>(R.id.roomsubbtn)
        var roomadult = view?.findViewById<ImageView>(R.id.adultsubbtn)
        var roomchild = view?.findViewById<ImageView>(R.id.childrensubbtn)

        var roomcount = view?.findViewById<TextView>(R.id.roomcount)
        var adultcount = view?.findViewById<TextView>(R.id.adultcount)
        var childcount = view?.findViewById<TextView>(R.id.childrencount)




//        handledata(addroom,roomsub,roomcount,count)
//        handledata(addadult,roomadult,adultcount,count)
//        handledata(addchild,roomchild,childcount,count)

        var a = roomdata(addroom,roomsub,roomcount )
        var b = adultdata(addadult,roomadult,adultcount )
        var c = childdata(addchild,roomchild,childcount )
       var submit =  view?.findViewById<MaterialButton>(R.id.guestApply)
        submit?.setOnClickListener{
            Toast.makeText(requireContext(), "  Guest Are  R:$roomc,A:$adultc,C:$childc, ", Toast.LENGTH_SHORT).show()
            view?.dismiss()

        }



    }

    private fun roomdata(addView: ImageView?,subView: ImageView?,textshowview:TextView?){
        var totalRoom = 0
        addView?.setOnClickListener {

            totalRoom += 1
            textshowview?.setText(totalRoom.toString())
            roomc = totalRoom

        }
        subView?.setOnClickListener {
            if (totalRoom==0){
                textshowview?.setText("0")
            }else{
                totalRoom -=1
                textshowview?.setText(totalRoom.toString())
            }
            roomc = totalRoom
        }


    }

    private fun adultdata(addView: ImageView?,subView: ImageView?,textshowview:TextView?) {

        var count1 = 0


        addView?.setOnClickListener {
            count1 += 1
            textshowview?.setText(count1.toString())
            adultc = count1
        }

        subView?.setOnClickListener {


            if (count1==0){
                textshowview?.setText("0")
            }else{
                count1-=1
                textshowview?.setText(count1.toString())
                adultc = count1
            }
        }

    }

    private fun childdata(addView: ImageView?,subView: ImageView?,textshowview:TextView?) {

        var count1 = 0


        addView?.setOnClickListener {
            count1 += 1
            textshowview?.setText(count1.toString())
            childc = count1
        }

        subView?.setOnClickListener {


            if (count1==0){
                textshowview?.setText("0")
            }else{
                count1-=1
                textshowview?.setText(count1.toString())
                childc = count1

            }
        }

    }



}

