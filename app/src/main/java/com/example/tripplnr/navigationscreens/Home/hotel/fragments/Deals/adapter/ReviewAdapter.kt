package com.example.tripplnr.navigationscreens.Home.hotel.fragments.Deals.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.*
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReviewAdapter(var list: List<reviewData>) :
    RecyclerView.Adapter<ReviewAdapter.InnerClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerClass {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return InnerClass(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: InnerClass, position: Int) {
        var item = list[position]
        GlobalScope.launch {
            holder.bind(list)
        }
//        val rate = item.Rating*10
//        holder.RatingBar.progress = rate.toInt()
//csdvfd
    }

    class InnerClass(view: View) : RecyclerView.ViewHolder(view) {
        var reviewerName = view.findViewById<TextView>(R.id.reviewerName)
        var reviewtext = view.findViewById<TextView>(R.id.reviewtext)
        var reviewButton = view.findViewById<MaterialButton>(R.id.reviewButton)

        suspend fun bind(list: List<reviewData>) {
            var item = list[position]

            reviewerName.setText(item.reviewerName)
            reviewtext.setText(item.reviewText)
            reviewButton.setText("${item.rate}")

        }

    }
}