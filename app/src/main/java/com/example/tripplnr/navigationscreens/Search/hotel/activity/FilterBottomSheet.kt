package com.example.tripplnr.navigationscreens.Search.hotel.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import com.example.tripplnr.R
import com.example.tripplnr.databinding.ActivityHotelList2Binding
import com.example.tripplnr.databinding.FilterBottomSheetBinding
import com.example.tripplnr.navigationscreens.DataCls.sortData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.RangeSlider
import io.opencensus.resource.Resource

class FilterBottomSheet: BottomSheetDialogFragment() {

    private lateinit var binding: FilterBottomSheetBinding
    val data = mutableListOf<String>()
    var sortingTech = ""
    private lateinit var sharee_pref :SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    var selected_tag_demo = setOf("freepark", "paylater", "all", "class2", "threestar")
    private var range = setOf(10f.toString(),1000f.toString())


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate your fragment layout here
        binding = FilterBottomSheetBinding.inflate(layoutInflater)
        sharee_pref = requireContext().getSharedPreferences("filter_bottom_sheet_selected",Context.MODE_PRIVATE)
        editor = sharee_pref.edit()


        val range_sf = sharee_pref.getStringSet("range_data",range)
        var range_in_float = mutableListOf<Float>()
        range_sf?.forEachIndexed { index, s ->
            when(index){
                0->{
                    binding.minRange.setText("$${s}")
                    range_in_float.add(s.toFloat())
                }
                1->{
                    binding.maxRange.setText("$$s")
                    range_in_float.add(s.toFloat())
                }
            }
        }
        binding.rangeSlider.values = range_in_float


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var selected_final =  sharee_pref.getStringSet("filter_data", selected_tag_demo)
        selected_final?.forEach {
           val selected_view = requireView().findViewWithTag<View>(it)
            selected_view.setBackgroundResource(R.drawable.bg_normal2)
            when(selected_view){
                is MaterialButton->{
                    selected_view.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                    selected_view.iconTint = ContextCompat.getColorStateList(requireContext(), R.color.white)

                    val color = ContextCompat.getColor(requireContext(), R.color.yellow)

                    selected_view.backgroundTintList = ColorStateList.valueOf(color)
                }
                is TextView->{
                    selected_view.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                }
                is LinearLayout->{
                    selected_view.forEach {

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
                    Toast.makeText(requireContext(), "unwanted view!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
//            bottom.setContentView(view)
//            bottom.show()
//            bottom.setCancelable(false)


            var closebtn =  binding.closebottomFilter
            closebtn.setOnClickListener{
                this.dismiss()
            }
            //range slider
            var rangeslider = binding.rangeSlider
            var rangeReset = binding.rangeReset
            rangeReset.setOnClickListener { rangeslider.apply {
                rangeslider.values = listOf(10f, 1000f)
            } }
            rangeslider.trackActiveTintList =
                ContextCompat.getColorStateList(requireContext(),R.color.yellow)!!
            rangeslider.addOnChangeListener { slider, value, fromUser ->
                val min = slider.values[0].toInt()
                val max = slider.values[1].toInt()
                // Perform actions based on the selected range values
                // For example, update a TextView with the selected range values
//                        textView.text = "Min: $min, Max: $max"
                var m_min =  binding.minRange
                var m_max = binding.maxRange
                editor.putStringSet("range_data",setOf(min.toString(),max.toString()))
                editor.apply()
                m_min.setText("$$min")
                m_max.setText("$$max")
            }



            /////

            var btn1 =  binding.btn1
            var btn2 =  binding.btn2
            var btn3 =  binding.btn3
            var btn4 =  binding.btn4
            var btn5 =  binding.btn5
            var hotelClass_reset =  binding.hotelclassReset
            val hotelClassList = listOf(btn1,btn2,btn3,btn4,btn5)
            dotask(hotelClassList as List<View>)
            hotelClass_reset.setOnClickListener {
                onClickResetBtn(hotelClass_reset,hotelClassList)
            }
            data.add(btn2?.tag.toString())


            //parking
            var freeparking  = binding.freePark
            var paidparking  = binding.paidPark
            var parking_reset =  binding.parkingReset
            val parkingTypeList = listOf(freeparking,paidparking)
            dotask(parkingTypeList as List<View>)
            parking_reset.setOnClickListener {
                onClickResetBtn(parking_reset,parkingTypeList)
            }
        data.add(freeparking.tag as String)


            //payment type
            var freeCancel  = binding.freeCancel
            var payLater  = binding.payLater
            var payment_reset =  binding.paymentReset
            val paymentTypeList = listOf(freeCancel,payLater)
            dotask(paymentTypeList as List<View>)
            payment_reset.setOnClickListener {
                onClickResetBtn(payment_reset,paymentTypeList)

            }
        data.add(payLater.tag as String)

            //property Type
            var allpropertytype  = binding.allPropertyType
            var allhotel  = binding.allHotel
            var apartment  = binding.apartment
            var property_reset =  binding.propertyReset
            val propertyType = listOf(allpropertytype,allhotel,apartment)
            dotask(propertyType as List<View>)
            property_reset.setOnClickListener { onClickResetBtn(property_reset,propertyType) }
            data.add(allpropertytype.tag as String)

            //customer review
            var one  = binding.onestar
            var two  = binding.twostar
            var three  = binding.threestar
            var four  = binding.fourstar
            var five  = binding.fivestar
            var customer_reset =  binding.customerreviewReset

            val customerReview = listOf(one,two,three,four,five)
            dotask(customerReview as List<View>)
            customer_reset.setOnClickListener { onClickResetBtn(customer_reset,customerReview) }
            data.add(three.tag.toString())



            //amenities type
//            var restaurant  = binding.m_restaurant
//            var fitness  = binding.m_fitness
//            var bar  = binding.m_bar
//            var wifi  = bottom.findViewById<MaterialButton>(R.id.m_wifi
//            var swim  = bottom.findViewById<MaterialButton>(R.id.m_swimming
//            var park  = bottom.findViewById<MaterialButton>(R.id.m_parking
//            var frontdesk  = bottom.findViewById<MaterialButton>(R.id.m_frontdesk
//            var amenities_reset =  bottom.findViewById<TextView>(R.id.amenitiesReset
//
//
//            val amenities = listOf(restaurant,fitness,bar,wifi,swim,park,frontdesk,)
//            dotask(amenities as List<View>)
//            amenities_reset?.setOnClickListener { onClickResetBtn(amenities_reset,amenities) }


            ////
            dotask(hotelClassList as List<View>)
            val apply = binding.applyBtn
            apply.setOnClickListener {
                Log.e("data filter", "doTask: data is :  $data", )
                editor.putStringSet("filter_data",data.toSet())
                editor.apply()

                this.dismissNow()

            }

//        dsjhvkjhvjkehkew


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
}
