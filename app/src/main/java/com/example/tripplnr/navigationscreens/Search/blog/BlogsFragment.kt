package com.example.tripplnr.navigationscreens.Search.blog

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.FragmentBlogsBinding
import com.example.tripplnr.navigationscreens.Home.adapter.TravelBlogAdapter
import com.example.tripplnr.navigationscreens.Home.dataclass.currencyData
import com.example.tripplnr.navigationscreens.Home.dataclass.homeItem

class BlogsFragment : Fragment() {
    private lateinit var binding: FragmentBlogsBinding
    private lateinit var rc: RecyclerView
    private lateinit var adapter: TravelBlogAdapter




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
        adapter = TravelBlogAdapter(datahandle(),null)
        rc.adapter = adapter


        var searchView = binding.searchView1

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Perform search based on query

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Perform search as query text changes (optional)
                filter(newText)
                return false
            }
        })





    }

    fun datahandle():MutableList<homeItem>{
        var list  = mutableListOf<homeItem>(
            homeItem(R.drawable.explore2,"the Golden Temple","12 may 23 ","1.32s",getString(R.string.testLine)),

            homeItem(R.drawable.exploreimg,"the Royal Temple","12 may 23 ","1.35s",getString(R.string.testLine)),
            homeItem(R.drawable.exploreimg,"the Swanrana mandhir ","12 may 23 ","1.11s",getString(R.string.testLine)),
            homeItem(R.drawable.explore2,"the love city","12 may 23 ","12.32s",getString(R.string.testLine)),
            homeItem(R.drawable.exploreimg,"the Punjaab","12 may 23 ","59.32s",getString(R.string.testLine)),

            )
        return list
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun filter(text: String) {

        var list11 = datahandle()
        // creating a new array list to filter our data.
        val filteredlist:MutableList<homeItem> = mutableListOf()

        // running a for loop to compare elements.
        for (item in list11) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.placetextuser.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)

            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
//            adapter.filterList(filteredlist)
            adapter.list = filteredlist
            adapter.notifyDataSetChanged()
            Log.e("test23", "filter: $filteredlist ", )
        }
    }


}