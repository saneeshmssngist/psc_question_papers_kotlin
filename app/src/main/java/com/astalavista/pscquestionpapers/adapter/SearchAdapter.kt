package com.astalavista.pscquestionpapers.adapter;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astalavista.pscquestionpapers.R
import com.astalavista.pscquestionpapers.models.QuestionPaper
import com.google.android.material.card.MaterialCardView

class SearchAdapter(
    var questionArray: ArrayList<QuestionPaper>,
    var onSearchListener: OnSearchListener
) :
    RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    interface OnSearchListener
    {
        fun onSearchTapped(position : Int)
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_data, parent, false)
        return SearchHolder(viiew)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {

        holder.txtTitle.text = questionArray[position].head
        holder.txtHead.text = questionArray[position].name
        holder.txtdate.text = questionArray[position].date

        when(position % 6)
        {
            0 ->  holder.cardView.setBackgroundResource(R.drawable.bg1)
            1 ->  holder.cardView.setBackgroundResource(R.drawable.bg2)
            2 ->  holder.cardView.setBackgroundResource(R.drawable.bg3)
            3 ->  holder.cardView.setBackgroundResource(R.drawable.bg4)
            4 ->  holder.cardView.setBackgroundResource(R.drawable.bg5)
            5 ->  holder.cardView.setBackgroundResource(R.drawable.bg6)
        }

        holder.cardViewSearch.setOnClickListener {
            onSearchListener.onSearchTapped(position)
        }

    }

    class SearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        var txtHead: TextView = itemView.findViewById(R.id.txtHead)
        var txtdate: TextView = itemView.findViewById(R.id.txtdate)
        var cardViewSearch: RelativeLayout = itemView.findViewById(R.id.cardViewSearch)
        var cardView: MaterialCardView = itemView.findViewById(R.id.cardView)

    }

}
