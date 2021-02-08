package com.astalavista.pscquestionpapers.adapter;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astalavista.pscquestionpapers.R
import com.astalavista.pscquestionpapers.models.QuestionPaper
import com.google.android.material.card.MaterialCardView

public class QuestionsRecentsAdapter(
    var arrayList: ArrayList<QuestionPaper>,
    val onRecentQuestionListener: OnRecentQuestionListener
) :
    RecyclerView.Adapter<QuestionsRecentsAdapter.HomeHolder>() {

    interface OnRecentQuestionListener
    {
        fun onItemTapped(position : Int)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recent_items, parent, false)
        return HomeHolder(view)
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int)
    {

        holder.txtHead.text = arrayList[position].name
        holder.txtSub.text = arrayList[position].date

        when(position % 6)
        {
            0 ->  holder.cardView.setBackgroundResource(R.drawable.bg1)
            1 ->  holder.cardView.setBackgroundResource(R.drawable.bg2)
            2 ->  holder.cardView.setBackgroundResource(R.drawable.bg3)
            3 ->  holder.cardView.setBackgroundResource(R.drawable.bg4)
            4 ->  holder.cardView.setBackgroundResource(R.drawable.bg5)
            5 ->  holder.cardView.setBackgroundResource(R.drawable.bg6)
        }

        holder.cardView.setOnClickListener {
            onRecentQuestionListener.onItemTapped(position)
        }

    }

    fun update(questionArray: java.util.ArrayList<QuestionPaper>) {
        this.arrayList = questionArray
        notifyDataSetChanged()
    }

    class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtHead: TextView = itemView.findViewById(R.id.txtHead)
        var cardView: MaterialCardView = itemView.findViewById(R.id.cardView)
        var txtSub: TextView = itemView.findViewById(R.id.txtSub)

    }

}
