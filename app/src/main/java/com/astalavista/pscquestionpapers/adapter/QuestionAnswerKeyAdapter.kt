package com.astalavista.pscquestionpapers.adapter;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astalavista.pscquestionpapers.R

public class QuestionAnswerKeyAdapter(
    var arrayList: ArrayList<String>
) :
    RecyclerView.Adapter<QuestionAnswerKeyAdapter.KeyHolder>() {

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_question_key, parent, false)
        return KeyHolder(view)
    }

    override fun onBindViewHolder(holder: KeyHolder, position: Int) {
        if (position < 9)
            holder.txtHead.text = "0"+(position + 1).toString() + "."
        else
            holder.txtHead.text = (position + 1).toString() + "."

        holder.txtAnswer.text = arrayList[position]

    }

    class KeyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtHead: TextView = itemView.findViewById(R.id.txtHead)
        var txtAnswer: TextView = itemView.findViewById(R.id.txtAnswer)

    }

}
