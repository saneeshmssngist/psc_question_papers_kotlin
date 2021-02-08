package com.astalavista.pscquestionpapers.adapter;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.astalavista.pscquestionpapers.R
import com.astalavista.pscquestionpapers.models.QuestionPaper
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat


public class SyllabusPapersAdapter(
    var context: Context,
    var arrayList: ArrayList<QuestionPaper>,
    val onRecentQuestionListener: OnRecentQuestionListener
) :
    RecyclerView.Adapter<SyllabusPapersAdapter.SylabusHolder>() {

    interface OnRecentQuestionListener {
        fun onItemTapped(position: Int)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SylabusHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_syallabus_data, parent, false)
        return SylabusHolder(view)
    }

    override fun onBindViewHolder(holder: SylabusHolder, position: Int) {

        holder.txtHead.text = arrayList[position].name

        when(arrayList[position].type)
        {
            "ldc","LDC" ->
            {
                holder.layoutBg.background = ContextCompat.getDrawable(context,R.drawable.ldc_logo)
            }
            "lgs","LGS" -> {
                holder.layoutBg.background = ContextCompat.getDrawable(context,R.drawable.lgs_logo)
            }
            "veo","VEO" -> {
                holder.layoutBg.background = ContextCompat.getDrawable(context,R.drawable.veo_logo)
            }
        }

        holder.txtDate.text = SimpleDateFormat("dd MMM yyyy")
            .format(SimpleDateFormat("yyyy-MM-dd").parse(arrayList[position].date))

//        when (position % 6) {
//            0 -> holder.cardView.setBackgroundResource(R.drawable.bg1)
//            1 -> holder.cardView.setBackgroundResource(R.drawable.bg2)
//            2 -> holder.cardView.setBackgroundResource(R.drawable.bg3)
//            3 -> holder.cardView.setBackgroundResource(R.drawable.bg4)
//            4 -> holder.csardView.setBackgroundResource(R.drawable.bg5)
//            5 -> holder.cardView.setBackgroundResource(R.drawable.bg6)
//        }

        holder.cardView.setOnClickListener {

            holder.cardView.strokeColor = ContextCompat.getColor(context,R.color.green_dark)
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context,R.color.grey))
            holder.txtHead.setTextColor(ContextCompat.getColor(context,R.color.green_dark))
            onRecentQuestionListener.onItemTapped(position)

        }

    }

    fun update(questionArray: ArrayList<QuestionPaper>) {
        this.arrayList = questionArray
        notifyDataSetChanged()
    }

    class SylabusHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtHead: TextView = itemView.findViewById(R.id.txtHead)
        var cardView: MaterialCardView = itemView.findViewById(R.id.cardView)
        var txtDate: TextView = itemView.findViewById(R.id.txtDate)
        var layoutBg: LinearLayout = itemView.findViewById(R.id.layoutBg)

    }

}
