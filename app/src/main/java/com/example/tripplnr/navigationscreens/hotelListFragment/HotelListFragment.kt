package com.example.tripplnr.navigationscreens.hotelListFragment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHotelListBinding
import com.example.tripplnr.navigationscreens.DataCls.sortData
import com.example.tripplnr.navigationscreens.Home.dataclass.hotelListClass
import com.example.tripplnr.navigationscreens.Home.hotel.HotelFragment
import com.example.tripplnr.navigationscreens.Search.hotel.activity.FilterBottomSheet
import com.example.tripplnr.navigationscreens.Search.hotel.activity.HotelList2Activity
import com.example.tripplnr.navigationscreens.hotelListFragment.adapter.Hotel_list_recyclerAdapter
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HotelListFragment : Fragment(), Hotel_list_recyclerAdapter.viewdetail {
    private lateinit var binding: FragmentHotelListBinding
    private lateinit var rc: RecyclerView
    private lateinit var query :String
    val data = mutableListOf<String>()
    var sortingTech = ""


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
            hotelListClass(R.drawable.exploreimg,"Taj Hotel","Punjab ,India",5f),
            hotelListClass(R.drawable.exploreimg,"The Royal Villa Hotel","Himachal ,India",4f),
            hotelListClass(R.drawable.exploreimg,"The Gold Hotel","Pathankot ,India",3f),
            hotelListClass(R.drawable.exploreimg,"Punjab Hotel","Pakistan ,India",4f),
        )
        return list

    }

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation", "InflateParams")
    fun doTask(view: View){
        var filter = binding.filterList
        var sort = binding.sortList
        view.setOnClickListener {
        val bottom = BottomSheetDialog(requireContext())
            when(view){
                filter-> {

//                    Allfun.openFragment(FilterBottomSheet(),requireParentFragment().parentFragmentManager)
                    val bottomSheetFragment = FilterBottomSheet()
                    bottomSheetFragment.show(requireParentFragment().parentFragmentManager, bottomSheetFragment.tag)
                    bottomSheetFragment.isCancelable = false
                }
                sort->{

                    var view = layoutInflater.inflate(R.layout.sort_dialog,null,false)
                    bottom.setContentView(view)
                    bottom.show()

                    var closebtn =  view.findViewById<ImageButton>(R.id.closeSort)
                    closebtn.setOnClickListener{
                        bottom.dismiss()
                    }


                    var recommended = bottom.findViewById<TextView>(R.id.recommended)
                    var imgpos1 = bottom.findViewById<ImageView>(R.id.imgpos1)

                    var prizeLtoH = bottom.findViewById<TextView>(R.id.prizeLowtoHigh)
                    var imgpos2 = bottom.findViewById<ImageView>(R.id.imgpos2)

                    var prizeHtoL = bottom.findViewById<TextView>(R.id.prizeHightoLow)
                    var imgpos3 = bottom.findViewById<ImageView>(R.id.imgpos3)

                    var reviews = bottom.findViewById<TextView>(R.id.sortByReviews)
                    var imgpos4 = bottom.findViewById<ImageView>(R.id.imgpos4)

                    var starRatingHtoL = bottom.findViewById<TextView>(R.id.starRatingHighToLow)
                    var imgpos5 = bottom.findViewById<ImageView>(R.id.imgpos5)

                    var distanceNtoF = bottom.findViewById<TextView>(R.id.distanceNearToFar)
                    var imgpos6 = bottom.findViewById<ImageView>(R.id.imgpos6)

                    val applyBtn = bottom.findViewById<MaterialButton>(R.id.sortApplyBtn)
                    applyBtn?.setOnClickListener{
                        Toast.makeText(requireContext(), sortingTech, Toast.LENGTH_SHORT).show()

                        bottom.dismiss()
                    }




                    val listSortType = listOf(sortData(recommended!!,imgpos1!!),sortData(prizeLtoH!!,imgpos2!!),sortData(prizeHtoL!!,imgpos3!!)
                        ,sortData(reviews!!,imgpos4!!),sortData(starRatingHtoL!!,imgpos5!!),sortData(distanceNtoF!!,imgpos6!!),
                    )
                    mSortData(listSortType as List<sortData>)
                bottom.setCancelable(false)
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    private fun dotask(list: List<View>) {

        for (button in list) {
            button.setOnClickListener {
                // Reset background for all buttons
                for (b in list) {
                    data.remove(b.tag)

                    b.setBackgroundResource(R.drawable.bg_normal)

                    b.backgroundTintMode = PorterDuff.Mode.SRC_ATOP
                    val color = ContextCompat.getColor(requireContext(), R.color.brown)

                    b.backgroundTintList = ColorStateList.valueOf(color)

                    when(b){
                        is TextView->{

                            b.setTextColor(resources.getColor(R.color.black))
                        }
                        is LinearLayout->{
                            b.forEach {
                            if(it is ImageView){
                                it.setImageResource(R.drawable.star_ic)
                            }
                        }}
                        else->{
                            Toast.makeText(requireContext(), "error occured!!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    if (b is MaterialButton){
                        b.iconTint = ContextCompat.getColorStateList(requireContext(), R.color.yellow)

                    }

                }

                // Change background of the clicked button
                button.setBackgroundResource(R.drawable.bg_normal2)
                data.add(it.tag.toString())


                when(button){


                    is TextView->{
                        button.setTextColor(resources.getColor(R.color.white))
                    }
                    is LinearLayout->{
                        button.forEach {

                            when(it){
                                is ImageView->{
//                                    it.imageTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
                                    it.setImageResource(R.drawable.star_white)
                                }
                                else->{
                                    Toast.makeText(requireContext(), "eroor", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }}
                    else->{
                        Toast.makeText(requireContext(), "error occured!!", Toast.LENGTH_SHORT).show()
                    }
                }
                val color = ContextCompat.getColor(requireContext(), R.color.yellow)

                button.backgroundTintList = ColorStateList.valueOf(color)
                if (button is MaterialButton){
                    button.iconTint = ContextCompat.getColorStateList(requireContext(), R.color.white)
                }



                when (button) {
                    list.get(0) -> {
                        // Actions for button1
//                        it.setBackgroundResource(R.drawable.card_shape)
                        Toast.makeText(requireContext(), "btn1", Toast.LENGTH_SHORT).show()
                    }
                    list.get(1) -> {
                        // Actions for button2
//                        it.setBackgroundResource(R.drawable.card_shape)
                        Toast.makeText(requireContext(), "btn2", Toast.LENGTH_SHORT).show()

                    }
                    list.get(2) -> {
                        // Actions for button3
//                        it.setBackgroundResource(R.drawable.card_shape)
                        Toast.makeText(requireContext(), "btn3", Toast.LENGTH_SHORT).show()

                    }
                    list.get(3) -> {
                        // Actions for button3
//                        it.setBackgroundResource(R.drawable.card_shape)
                        Toast.makeText(requireContext(), "btn4", Toast.LENGTH_SHORT).show()

                    }
                    list.get(4) -> {
                        // Actions for button3
//                        it.setBackgroundResource(R.drawable.card_shape)
                        Toast.makeText(requireContext(), "btn5", Toast.LENGTH_SHORT).show()

                    }list.get(5) -> {
                        // Actions for button3
//                        it.setBackgroundResource(R.drawable.card_shape)
                        Toast.makeText(requireContext(), "btn", Toast.LENGTH_SHORT).show()

                    }
                    else->{
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

    }
    private fun mSortData(list: List<sortData>){
        for (item in list){

            item.text.setOnClickListener {
                // Reset background for all buttons
                for (mItem in list){
                    mItem.image.visibility = View.INVISIBLE
                    mItem.text.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                item.text.setTextColor(ContextCompat.getColor(requireContext(),R.color.yellow))
                item.image.visibility = View.VISIBLE
                sortingTech = item.text.tag.toString()

            }

        }

    }
    private fun onClickResetBtn(textView:TextView,list: List<View>){

        when(textView.id){
            R.id.rangeReset->{ setNormalbg(list) }
            R.id.parkingReset->{  setNormalbg(list) }
            R.id.paymentReset->{ setNormalbg(list)}
            R.id.propertyReset->{ setNormalbg(list)}
            R.id.hotelclassReset->{ setNormalbg(list)}
            R.id.customerreviewReset->{ setNormalbg(list)}
            R.id. amenitiesReset->{ setNormalbg(list)}
        }

    }
    @SuppressLint("ResourceType")
    private fun setNormalbg(list: List<View>) {
        for (item in list) {
            item.setBackgroundResource(R.drawable.bg_normal)

            item.backgroundTintMode = PorterDuff.Mode.MULTIPLY
            val color = ContextCompat.getColor(requireContext(), R.color.white)
//                materialButton.backgroundTintList = ColorStateList.valueOf(color)
            item.backgroundTintList = ColorStateList.valueOf(color)


            when(item){
                is TextView->{
                    item.setTextColor(resources.getColor(R.color.black))
                }
                is LinearLayout->{
                    item.forEach {
                        if (it is ImageView){
                            it.setImageResource(R.drawable.star_ic)
                        }
                    }
                }

                else->{
                    Toast.makeText(requireContext(), "error occured!!", Toast.LENGTH_SHORT).show()
                }

            }
            if (item is MaterialButton){
                item.iconTint = ContextCompat.getColorStateList(requireContext(), R.color.yellow)
            }

        }
    }

    override fun checkDetails(view: View, position: Int) {
        Allfun.openWeb(requireContext())

    }

    override fun onCardClicks(
        view: View,
        position: Int,
        placetxt: Pair<String, String>,
        rating: Float
    ) {
        val bundle = Bundle()
//                args.putParcelable("guest", guestLiveData.value)
//                args.putString("date",dateLiveData.value)
        bundle.putString("place", placetxt.first)
        bundle.putString("city", placetxt.second)
        bundle.putFloat("rate", rating)


//
        val newFragment = HotelFragment()
//       var HotelFragment_id =  newFragment.id
        newFragment.arguments = bundle
        val fragmentManager = requireParentFragment().parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, newFragment)
        transaction.addToBackStack("hotel_fragment")
        transaction.commit()
//        findNavController().navigate(HotelFragment_id,bundle)
    }






}