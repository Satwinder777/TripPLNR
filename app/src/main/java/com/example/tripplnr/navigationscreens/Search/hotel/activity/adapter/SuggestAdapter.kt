package com.example.tripplnr.navigationscreens.Search.hotel.activity.adapter


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
//import com.google.android.libraries.places.api.net.Places.getGeoDataClient
import com.google.android.gms.tasks.Tasks
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class PlaceAutocompleteAdapter(context: Context, private val placesClient: PlacesClient) :
    ArrayAdapter<Place>(context, android.R.layout.simple_dropdown_item_1line) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val geoDataClient = Places.createClient(context)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false)
        val place = getItem(position)

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = place?.name

        return view
    }

    override fun getItem(position: Int): Place? {
        return super.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val predictions = ArrayList<AutocompletePrediction>()

                constraint?.let {
                    val request = FindAutocompletePredictionsRequest.builder()
                        .setQuery(constraint.toString())
                        .build()

                    val task = placesClient.findAutocompletePredictions(request)
                    Tasks.await(task, 60, TimeUnit.SECONDS)

                    if (task.isSuccessful) {
                        val response = task.result
                        response?.let { predictionResponse ->
                            predictions.addAll(predictionResponse.autocompletePredictions)
                        }
                    }
                }

                results.values = predictions
                results.count = predictions.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clear()
                results?.values?.let { filteredPlaces ->
                    addAll(filteredPlaces as List<Place>)
                }
                notifyDataSetChanged()
            }
        }
    }
}