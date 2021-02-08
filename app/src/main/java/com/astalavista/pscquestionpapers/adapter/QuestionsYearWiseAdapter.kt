package com.astalavista.pscquestionpapers.adapter;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astalavista.pscquestionpapers.R
import com.astalavista.pscquestionpapers.models.QuestionPaper
import com.google.android.material.card.MaterialCardView

public class QuestionsYearWiseAdapter(
    var arrayList: ArrayList<QuestionPaper>,
    var onYearWiseQuestionListener: OnYearWiseQuestionListener
) :
    RecyclerView.Adapter<QuestionsYearWiseAdapter.HomeHolder>() {

    interface OnYearWiseQuestionListener
    {
        fun onItemTapped(position : Int)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_yearwise_items, parent, false)
        return HomeHolder(view)
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {

        holder.txtHead.text = arrayList[position].name
        holder.txtdate.text = arrayList[position].date

        holder.cardView.setOnClickListener {
            onYearWiseQuestionListener.onItemTapped(position)
        }

    }

    fun update(questionArray: java.util.ArrayList<QuestionPaper>) {
        this.arrayList = questionArray
        notifyDataSetChanged()
    }

    class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtHead: TextView = itemView.findViewById(R.id.txtHead)
        var txtdate: TextView = itemView.findViewById(R.id.txtdate)
        var cardView: MaterialCardView = itemView.findViewById(R.id.cardView)

    }

}
