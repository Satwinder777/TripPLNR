package com.example.tripplnr.navigationscreens.Account.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.databinding.ActivityCurrencyBinding
import com.example.tripplnr.navigationscreens.Account.AccountFragment
import com.example.tripplnr.navigationscreens.Home.adapter.CurrencyRecyclerAdapter
import com.example.tripplnr.navigationscreens.Home.dataclass.currencyData
import com.example.tripplnr.navigationscreens.objectfun.Allfun
import java.util.*

class CurrencyActivity : AppCompatActivity(),CurrencyRecyclerAdapter.callfun {
    private lateinit var binding :ActivityCurrencyBinding
    private lateinit var suggestAdapter : ArrayAdapter<*>
//    private lateinit var rcCurr :RecyclerView
//    private lateinit var adapter :CurrencyRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        rcCurr = binding.currencyRecyclerView
//        rcCurr.layoutManager = LinearLayoutManager(this)
//       adapter = CurrencyRecyclerAdapter(emptylist(),this)
//        rcCurr.adapter = adapter


//        var searchView = binding.searchView
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                // Perform search based on query
//
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                // Perform search as query text changes (optional)
//                filter(newText)
//                return false
//            }
//        })
        var currencyPicker =  binding.currencyPicker
        currencyPicker.apply {
            threshold = 0

              suggestAdapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, resources.getStringArray(R.array.currency) )
            setAdapter(suggestAdapter)
            this.setOnItemClickListener { parent, view, position, id ->
                Allfun.currencyData.value = currencyPicker.text.toString()

                var value = currencyPicker.text.toString()
                val resultIntent = intent
                resultIntent.putExtra("key", value) // Replace "key" with your desired key and value with the actual data
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
//                Log.e("checkcurrency", "onCreate: ${view.transitionName},${parent.get(position).transitionName} ", )
            }
            this.setOnClickListener{
                showDropDown()
            }
            //  performCompletion()


        }


        binding.backbtncurrencyActivity.setOnClickListener { onBackPressed() }
    }

    @SuppressLint("NotifyDataSetChanged")
//    private fun filter(text: String) {
//
//        var list11 = currenData()
//        // creating a new array list to filter our data.
//        val filteredlist:MutableList<currencyData> = mutableListOf()
//
//        // running a for loop to compare elements.
//        for (item in list11) {
//            // checking if the entered string matched with any item of our recycler view.
//            if (item.currency.toLowerCase().contains(text.toLowerCase())) {
//                // if the item is matched we are
//                // adding it to our filtered list.
//                filteredlist.add(item)
//
//            }
//        }
//        if (filteredlist.isEmpty()) {
//            // if no item is added in filtered list we are
//            // displaying a toast message as no data found.
//            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
//        } else {
//            // at last we are passing that filtered
//            // list to our adapter class.
////            adapter.filterList(filteredlist)
//            adapter.list = filteredlist
//            adapter.notifyDataSetChanged()
//            Log.e("test23", "filter: $filteredlist ", )
//        }
//    }
    private fun emptylist():MutableList<currencyData>{

        var list = mutableListOf<currencyData>(

        )
       return list
    }

    override fun showcurrency(currency: String) {
        Allfun.currencyData.value = currency
        var activity = AccountFragment()
        activity.onCreate(null)
    }


}