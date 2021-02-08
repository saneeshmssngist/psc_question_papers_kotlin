package com.astalavista.pscquestionpapers.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astalavista.pscquestionpapers.R
import com.astalavista.pscquestionpapers.activities.QuestionViewActivity
import com.astalavista.pscquestionpapers.adapter.SyllabusHeadsAdapter
import com.astalavista.pscquestionpapers.models.QuestionHead
import com.astalavista.pscquestionpapers.retrofit.DataManager
import com.astalavista.pscquestionpapers.retrofit.RetrofitCallBack
import kotlinx.android.synthetic.main.fragment_syallabus.view.*
import kotlinx.android.synthetic.main.tool_bar_layout.view.*

class SyllabusWiseFragment : Fragment() {

    lateinit var questionPaperArray: ArrayList<QuestionHead>
    lateinit var views: View
    lateinit var adapterSyllabusHeadsAdapter: SyllabusHeadsAdapter
    var headsDatas: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        views = inflater.inflate(R.layout.fragment_syallabus, null)

        views.layoutBack.visibility = View.GONE
        views.txtHead.text = "Exams Wise"

        initViews()

        return views
    }

    override fun onResume() {
        super.onResume()
        setDatas()

    }

    private fun initViews() {

        views!!.recyclervWHeads.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adapterSyllabusHeadsAdapter = SyllabusHeadsAdapter(
            this.context!!,
            ArrayList<QuestionHead>(), object : SyllabusHeadsAdapter.OnQuestionListener {
                override fun onItemTapped(materPos: Int, position: Int) {

                    startActivity(
                        Intent(activity, QuestionViewActivity::class.java)
                            .putExtra(
                                "question_data",
                                questionPaperArray.get(materPos).arrayListData.get(position)
                            )
                    )

                }
            })
        views!!.recyclervWHeads.adapter = adapterSyllabusHeadsAdapter

    }

    private fun setDatas() {

        views.recyclervWHeads.visibility = View.VISIBLE
        views.progressSearch.visibility = View.VISIBLE
        DataManager.getDatamanager().getSyllabusData(object : RetrofitCallBack<QuestionHead> {
            override fun Success(data: QuestionHead) {

                views.progressSearch.visibility = View.GONE
                questionPaperArray = data.arrayListHeads
                adapterSyllabusHeadsAdapter.update(questionPaperArray)

            }

            override fun Failure(data: String) {
                views.progressSearch.visibility = View.GONE
            }

        })
    }


}
