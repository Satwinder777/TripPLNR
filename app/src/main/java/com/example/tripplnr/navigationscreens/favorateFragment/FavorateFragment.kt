package com.example.tripplnr.navigationscreens.favorateFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentFavorateBinding
import com.example.tripplnr.navigationscreens.Home.adapter.TravelBlogAdapter
import com.example.tripplnr.navigationscreens.Home.dataclass.homeItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class FavorateFragment : Fragment() {
    private lateinit var binding : FragmentFavorateBinding
    private lateinit var rc : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavorateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rc = binding.favorateRecyclerView
        rc.layoutManager = LinearLayoutManager(requireContext())
        var adapter = TravelBlogAdapter(datahandle(),null)
        rc.adapter = adapter

        binding.backbtnFavorateFragment.setOnClickListener {
//            childFragmentManager.popBackStack()
//            parentFragmentManager.popBackStack()

            findNavController().navigateUp()
        }

    }

    fun datahandle():List<homeItem>{
        var list  = listOf<homeItem>(
            homeItem(R.drawable.explore2,"the Golden Temple","12 may 23 ","1.32s",getString(R.string.testLine)),

            homeItem(R.drawable.exploreimg,"the Royal Temple","12 may 23 ","1.35s",getString(R.string.testLine)),
            homeItem(R.drawable.exploreimg,"the Swanrana mandhir ","12 may 23 ","1.11s",getString(R.string.testLine)),
            homeItem(R.drawable.explore2,"the love city","12 may 23 ","12.32s",R.string.testLine.toString()),
            homeItem(R.drawable.exploreimg,"the Punjaab","12 may 23 ","59.32s",R.string.testLine.toString()),

            )
        return list
    }
}