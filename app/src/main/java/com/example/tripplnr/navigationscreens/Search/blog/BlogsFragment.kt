package com.example.tripplnr.navigationscreens.Search.blog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentBlogsBinding
import com.example.tripplnr.navigationscreens.Home.adapter.TravelBlogAdapter
import com.example.tripplnr.navigationscreens.Home.dataclass.homeItem

class BlogsFragment : Fragment() {
    private lateinit var binding: FragmentBlogsBinding
    private lateinit var rc: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBlogsBinding.inflate(layoutInflater)
//        view.findViewById<>()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        findNavController().navigate(R.id.blogsFragment)

        rc = binding.blogRecyclerView
        rc.layoutManager = LinearLayoutManager(requireContext())
        var adapter = TravelBlogAdapter(datahandle(),null)
        rc.adapter = adapter








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