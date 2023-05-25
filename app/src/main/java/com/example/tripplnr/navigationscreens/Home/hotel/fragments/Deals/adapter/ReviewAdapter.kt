package com.example.tripplnr.navigationscreens.Home.hotel.fragments.Deals.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tripplnr.R
import com.example.tripplnr.navigationscreens.Home.dataclass.*
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReviewAdapter(var list: List<reviewData>,var context: Context) :
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
//        var item = list[position]
        GlobalScope.launch {
            holder.bind(list, context )




        }

//        val rate = item.Rating*10
//        holder.RatingBar.progress = rate.toInt()
//csdvfd
    }

    class InnerClass(view: View) : RecyclerView.ViewHolder(view) {
        var reviewerName = view.findViewById<TextView>(R.id.reviewerName)
        var reviewtext = view.findViewById<TextView>(R.id.reviewtext)
        var reviewtxt = view.findViewById<TextView>(R.id.seeMoreReviews)
        var reviewButton = view.findViewById<MaterialButton>(R.id.reviewButton)

        @SuppressLint("ResourceAsColor", "ResourceType")
        suspend fun bind(list: List<reviewData>,context:Context) {
            var item = list[position]


            reviewerName.setText(item.reviewerName)
            reviewtext.setText(item.reviewText)
            reviewButton.setText("${item.rate}")

            var isLessReviewText:Boolean = true
            reviewtext.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val lineCount = reviewtext.lineCount
                    // Use the lineCount as per your requirement
                    // You can perform any logic based on the line count here

                    // Remove the layout listener to avoid redundant calls
                    reviewtext.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    Log.e("test11", "bind: {${lineCount}}")

                    //start
                    if (reviewtext.lineCount>1){


                        reviewtxt.setOnClickListener {
                            if(isLessReviewText==true){
                                reviewtext.maxLines = Int.MAX_VALUE
                                reviewtxt.setText(context.getText(R.string.readless))
                                isLessReviewText = false

                            }
                            else{
                                reviewtext.setLines(1)
                                reviewtxt.setText(context.getText(R.string.readextra))
                                isLessReviewText = true
                            }


                        }
                        var TAG = "test25"

                        Log.e(TAG, "bind:  visibility visible :reviewtxt ifblock", )


                    }
                    else{
                        var TAG = "test25"

                        Log.e(TAG, "bind:  visibility gone :reviewtxt  ${reviewtext.lineCount}", )
                        reviewtxt.visibility = View.GONE
                    }
                    //end


                }
            })



//            reviewtext.maxLines = Int.MAX_VALUE
        }

    }
}