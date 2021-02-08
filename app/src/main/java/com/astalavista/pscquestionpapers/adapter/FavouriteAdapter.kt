package com.astalavista.pscquestionpapers.adapter;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astalavista.pscquestionpapers.R
import com.astalavista.pscquestionpapers.models.QuestionPaper
import com.google.android.material.card.MaterialCardView

class FavouriteAdapter(
    var questionArray: ArrayList<QuestionPaper>,
    var onFavouriteListener: OnFavouriteListener
) :
    RecyclerView.Adapter<FavouriteAdapter.SearchHolder>() {

    interface OnFavouriteListener
    {
        fun onFavouriteTapped(position : Int)
    }

    fun update(searchQuestionData: java.util.ArrayList<QuestionPaper>) {
        this.questionArray = searchQuestionData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return questionArray.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {

        var viiew =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favourite, parent, false)
        return SearchHolder(viiew)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {

        holder.txtHead.text = questionArray[position].name
        holder.txtdate.text = questionArray[position].date

        holder.cardViewfav.setOnClickListener {
            onFavouriteListener.onFavouriteTapped(position)
        }

    }

    class SearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtHead: TextView = itemView.findViewById(R.id.txtHead)
        var txtdate: TextView = itemView.findViewById(R.id.txtdate)
        var cardViewfav: MaterialCardView = itemView.findViewById(R.id.cardViewfav)

    }

}
