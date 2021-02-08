package com.astalavista.pscquestionpapers.adapter;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.astalavista.pscquestionpapers.R
import com.google.android.material.card.MaterialCardView

public class QuestionYearListAdapter(val context : Context,
    var arrayList: ArrayList<String>,
    val yeartappedListener: OnYearClickListener
) :
    RecyclerView.Adapter<QuestionYearListAdapter.HomeHolder>() {

    var selectedPosition : Int = 0

    interface OnYearClickListener
    {
        fun onYearTapped (position: Int) {}
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_year_list, parent, false)
        return HomeHolder(view)
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {

        holder.txtHead.text = arrayList[position]

        if (position == selectedPosition) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green))
            holder.cardView.strokeColor = ContextCompat.getColor(context,R.color.green)
            holder.txtHead.setTextColor(ContextCompat.getColor(context, R.color.white))
            holder.imgArrow.visibility = View.VISIBLE
        }
        else
        {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
            holder.cardView.strokeColor = ContextCompat.getColor(context,R.color.grey)
            holder.txtHead.setTextColor(ContextCompat.getColor(context, R.color.black_light))
            holder.imgArrow.visibility = View.GONE
        }

        holder.cardView.setOnClickListener {

            selectedPosition = position
            yeartappedListener.onYearTapped(position)
            notifyDataSetChanged()
        }

    }

    fun update(data: java.util.ArrayList<String>) {
        this.arrayList = data
        notifyDataSetChanged()
    }

    class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtHead: TextView = itemView.findViewById(R.id.txtHead)
        var cardView: MaterialCardView = itemView.findViewById(R.id.cardView)
        var imgArrow: ImageView = itemView.findViewById(R.id.imgArrow)

    }

}
