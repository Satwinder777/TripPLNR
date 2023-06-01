package com.example.tripplnr.navigationscreens.Home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentHomeBinding
import com.example.tripplnr.navigationscreens.DataCls.Massage
import com.example.tripplnr.navigationscreens.Home.adapter.HotelRecyclerViewAdapter
import com.example.tripplnr.navigationscreens.Home.adapter.TravelBlogAdapter
import com.example.tripplnr.navigationscreens.Home.dataclass.travelBlogItem
import com.example.tripplnr.navigationscreens.Home.hotel.HotelFragment
import com.example.tripplnr.navigationscreens.LiveDataVM.Live
import com.example.tripplnr.navigationscreens.Repository.TripRepository
import com.example.tripplnr.navigationscreens.ViewModel.MyViewModel
import com.example.tripplnr.navigationscreens.ViewModel.ViewModelFactory
import com.example.tripplnr.navigationscreens.favorateFragment.ViewModel.FavorateViewModel


import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), TravelBlogAdapter.onItemClick {
    private lateinit var binding :FragmentHomeBinding
    private lateinit var rcTravelBlog :RecyclerView
    private lateinit var popularHotelRc :RecyclerView
    private lateinit var adapter :TravelBlogAdapter

    var favorateList = MutableLiveData<MutableList<travelBlogItem>>()
    private val viewModelFavorate by viewModels<FavorateViewModel>()



//    private var viewModel: MyViewModel by viewModels()
//    private val viewModel: MyViewModel by viewModels()
//    val viewModelFactory = MyViewModelFactory(myParameter)
//    viewModel = ViewModelProvider(this, viewModelFactory).get(MyViewModel::class.java)
    val viewmodelfactory = ViewModelFactory(TripRepository(Massage("satwinderSherGillk")),null)
    val favorateFactory = ViewModelFactory(null,favorateList)





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentHomeBinding.inflate(layoutInflater)
        Log.e("testqwer", "  test oncreate ", )

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.P)
    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcTravelBlog = binding.rcTravelBlog
        popularHotelRc = binding.popularHotelRc
        Log.e("testqwer", "  onViewCreated  ", )


        val finalviewmodel = ViewModelProvider(this,favorateFactory).get(FavorateViewModel::class.java)


        GlobalScope.launch {
//            val adapter = HotelRecyclerViewAdapter(hotelData())
//            popularHotelRc.adapter = adapter
        }
        val chip: Chip = binding.viewHotelCard as Chip

        val outlineSpotShadowColor = ContextCompat.getColor(requireContext(), R.color.yellow)


        val chipDrawable = chip.chipDrawable as? ChipDrawable
//            chipDrawable?.setSpotShadowColor(outlineSpotShadowColor)
//            chipDrawable?.setShadowColor(outlineSpotShadowColor)
//            chip.outlineSpotShadowColor = outlineSpotShadowColor
        chip.elevation = 900f
        chip.setShadowLayer(0f,10f,50f,outlineSpotShadowColor)
        chip.outlineSpotShadowColor = outlineSpotShadowColor
        binding.viewHotelCard.setOnClickListener {
            findNavController().navigate(R.id.searchFragment )


//            chip.outlineAmbientShadowColor = outlineSpotShadowColor
        }


            try {
                var TAG = "test000"
                val viewmodel = ViewModelProvider(this,viewmodelfactory).get(MyViewModel::class.java)

                viewmodel.datahandle()

                    viewmodel.getItemList().observe(viewLifecycleOwner, Observer { items ->
                        var adapter = TravelBlogAdapter(items,this,favorateList,this)


                        rcTravelBlog.adapter = adapter
                        adapter.notifyDataSetChanged()
                    })


                GlobalScope.launch {
                    delay(5000)

                    viewmodel.additem(travelBlogItem(R.drawable.explore2,"the shergill palace","2 mint ago","2mint "))
                    Log.e(TAG, "onViewCreated: first task done ", )
                    delay(5000)
                    viewmodel.remove(3)
                    Log.e(TAG, "onViewCreated: second also done ", )

                }

            }
            catch (e:Exception){
                Log.e("error1", "onViewCreated: ${e.message}", )
            }


            val viewmodel = ViewModelProvider(this,viewmodelfactory).get(MyViewModel::class.java)

            viewmodel.hotelData()
            viewmodel.rc2List().observe(viewLifecycleOwner, Observer { items ->
                val adapter = HotelRecyclerViewAdapter(items)
                popularHotelRc.adapter  = adapter
                adapter.notifyDataSetChanged()


            })
//            popularHotelRc.adapter = adapter



//        favorateList.observe(viewLifecycleOwner, Observer {
//            Log.e("loveData", "dataFavorate : $it", )
//        })


    }
//    fun datahandle():MutableList<travelBlogItem>{
//        var list  = mutableListOf<travelBlogItem>(travelBlogItem(R.drawable.explore2,"the Golden Temple","12 may 23 ","1.32s",getString(R.string.testLine)),
//
//            travelBlogItem(R.drawable.exploreimg,"the Royal Temple","12 may 23 ","1.35s",getString(R.string.testLine)),
//            travelBlogItem(R.drawable.exploreimg,"the Swanrana mandhir ","12 may 23 ","1.11s",getString(R.string.testLine)),
//            travelBlogItem(R.drawable.explore2,"the love city","12 may 23 ","12.32s",R.string.testLine.toString()),
//            travelBlogItem(R.drawable.exploreimg,"the Punjaab","12 may 23 ","59.32s",R.string.testLine.toString()),
//
//        )
//        return list
//    }

//    fun hotelData():List<hotelTitle>{
//        val hotelchildData  = listOf<hotelchild>(
//            hotelchild(R.drawable.explore2,"Taj Hotel","Amritsar","3.3"),
//            hotelchild(R.drawable.exploreimg,"The Bill Gates","America,Us","4.5"),
//            hotelchild(R.drawable.explore2,"Punjab Hotel","Amritsar,Punjab","5.9"),
//            hotelchild(R.drawable.exploreimg,"Chandighar Hotel","Chandighar, India","4.7"),
//            hotelchild(R.drawable.explore2,"Us Hotel","Us,Amercia","4.8"),
//            )
//        var list  = listOf<hotelTitle>(
//            hotelTitle("Top Hotel",hotelchildData),
//            hotelTitle("Best Hotel",hotelchildData),
//            hotelTitle("Old Hotel",hotelchildData),
//            hotelTitle("Gold Hotel",hotelchildData),
//
//            )
//        return list
//    }

    override fun onclickItem(position: Int) {
//        val fragmentManager = supportFragmentManager // For activities
// OR
        val newFragment = HotelFragment()
//            val targetFragment = TargetFragment()
        val fragmentManager = requireParentFragment().parentFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()



//        var triprepo = TripRepository(Massage("satwindersinghshergill"))
//         viewModel = ViewModelProvider(this).get(TripViewModel::class.java)
//        var a =
//        println(a)
//        Log.e("testadd", "onclickItem: adddd  :${viewModel.add()}", )
//        Log.e("testadd1", "onclickItem: sub  :${viewModel.sub()}", )





    }

    override fun onfavoratebtnClicks(position: Int) {
//        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation)
//        bottomNavigationView.menu.getItem(2).isVisible = true
        //        findNavController().navigate(R.id.favorateFragment)



//        bottomNavigationView.menu.findItem(R.id.blogsFragment).setChecked(true)
//        val newFragment = FavorateFragment()
////            val targetFragment = TargetFragment()
//        val fragmentManager = requireParentFragment().parentFragmentManager
//        val transaction = fragmentManager.beginTransaction()
//        transaction.replace(R.id.nav_host_fragment, newFragment)
//        transaction.addToBackStack(null)
//        transaction.commit()

    }

    override fun showtext(position: Int) {

    }

    override fun addifFAv(
        itemposition: travelBlogItem,
        favorateBtn: ImageView,
        currentblog: travelBlogItem,
        position: Int,
        list: MutableList<travelBlogItem>
    ) {
        if (list[position].isfavorate == true){
            favorateBtn.setImageResource(R.drawable.favo_ic)
            Live.data1?.add(currentblog)

        }
        else{
            favorateBtn.setImageResource(R.drawable.fav_empty_ic)

//                    currentList.add(currentblog)

        }
    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun showtext(position: Int) {
////        adapter.data
//    }


    private fun showFavBlog(items: MutableList<travelBlogItem>?){

//        if (items != null) {
//            for (i in items){
//
//                if (items.contains(travelBlogItem(isfavorate = true)) == true){
//                    items[0].isfavorate
//                    var isFav = view?.findViewById<ImageView>(R.id.addtoFavorateIV)
//
//                }
//            }
//
//        }
    }
//    private fun myfun(itemposition: travelBlogItem,favorateBtn: ImageView,item: travelBlogItem){
//
//    }
}
