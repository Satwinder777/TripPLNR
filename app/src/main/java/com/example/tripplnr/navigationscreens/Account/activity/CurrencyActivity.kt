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

class CurrencyActivity : AppCompatActivity(), CurrencyRecyclerAdapter.callfun {
    private lateinit var binding :ActivityCurrencyBinding
//    private lateinit var suggestAdapter : ArrayAdapter<*>
    private lateinit var rc :RecyclerView
    private lateinit var adapter0 :CurrencyRecyclerAdapter
    private lateinit var currencyPicker : AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrencyBinding.inflate(layoutInflater)
        setContentView(binding.root)


            rc = binding.currencyRecyclerView


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
      currencyPicker =  binding.currencyPicker
        currencyPicker.apply {
            threshold = 0


//              suggestAdapter = ArrayAdapter(context, android.R.layout.simple_dropdown_item_1line, resources.getStringArray(R.array.currency) )
            adapter0 = CurrencyRecyclerAdapter(list_currency(),this@CurrencyActivity )

            rc.adapter = adapter0

//
            //  performCompletion()


        }
        currencyPicker.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // This method is called before the text changes
                // You can perform any necessary actions here
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // This method is called when the text changes
                // You can implement your filtering logic here

                val newText = s.toString().trim() // Get the new text entered

                // Filter the data based on the new text
                val filteredData = filterData(newText)

                // Update the adapter with the filtered data
                adapter0.list = filteredData
//          adapter0 = autoCompleteTextView.adapter as ArrayAdapter<String>
//                adapter.clear()
//                adapter.addAll(filteredData)
                adapter0.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable?) {
                // This method is called after the text changes
                // You can perform any necessary actions here
            }
        })

        // Function to filter data based on the text entered



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




        val value = currency
        Log.e("value_currency", "showcurrency: $value", )
        val resultIntent = intent
        resultIntent.putExtra("key", value) // Replace "key" with your desired key and value with the actual data
        setResult(Activity.RESULT_OK, resultIntent)
        finish()

    }

    private fun list_currency():MutableList <currencyData>{




       var list = mutableListOf(
            currencyData("INR"),
            currencyData("USD"),
            currencyData("US Dollar (USD)"),
            currencyData("Euro (EUR)"),
            currencyData("British Pound (GBP)"),
            currencyData("Japanese Yen (JPY)"),
            currencyData("Swiss Franc (CHF)"),
            currencyData("Canadian Dollar (CAD)"),
            currencyData("Australian Dollar (AUD)"),
            currencyData("New Zealand Dollar (NZD)"),
            currencyData("Chinese Yuan (CNY)"),
            currencyData("Indian Rupee (INR)"),
            currencyData("Russian Ruble (RUB)"),
            currencyData("Brazilian Real (BRL)"),
            currencyData("South African Rand (ZAR)"),

        )
        return list
    }
    private fun filterData(text: String): MutableList<currencyData> {
        // Implement your filtering logic here
        // Return the filtered data as a List<String>
        // You can query from a data source or manipulate an existing list of data

       var filterlist =  list_currency().filter { it.currency.toLowerCase().contains(text) }.toMutableList()
        return filterlist
    }

}