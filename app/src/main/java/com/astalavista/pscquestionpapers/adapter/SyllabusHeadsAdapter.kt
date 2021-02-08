package com.astalavista.pscquestionpapers.adapter;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astalavista.pscquestionpapers.R
import com.astalavista.pscquestionpapers.models.QuestionHead

public class SyllabusHeadsAdapter(
    val context: Context,
    var arrayList: ArrayList<QuestionHead>,
    var questionPaperTapped: OnQuestionListener
) :
    RecyclerView.Adapter<SyllabusHeadsAdapter.SylabusHolder>() {

    interface OnQuestionListener {
        fun onItemTapped(materPos: Int, position: Int)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SylabusHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_syllabus_heads, parent, false)
        return SylabusHolder(view)
    }

    override fun onBindViewHolder(holder: SylabusHolder, position: Int) {

        var syllabusData = arrayList[position];
        holder.txtHead.text = syllabusData.syllabusName

        holder.recyclerViewPapers.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        var adapterSyllabusHeadsAdapter = SyllabusPapersAdapter(context,syllabusData.arrayListData,
            object : SyllabusPapersAdapter.OnRecentQuestionListener {
                override fun onItemTapped(pos: Int) {
                    questionPaperTapped.onItemTapped(position, pos)

                }

            })
        holder.recyclerViewPapers.adapter = adapterSyllabusHeadsAdapter

    }

    fun update(questionPaperArray: java.util.ArrayList<QuestionHead>) {
        this.arrayList = questionPaperArray
        notifyDataSetChanged()
    }

    class SylabusHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtHead: TextView = itemView.findViewById(R.id.txtHead)
        var recyclerViewPapers: RecyclerView = itemView.findViewById(R.id.recyclerViewPapers)

    }

}
