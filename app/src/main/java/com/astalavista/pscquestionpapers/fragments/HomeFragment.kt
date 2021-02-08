package com.astalavista.pscquestionpapers.fragments

import android.content.Intent
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView;
import com.astalavista.pscquestionpapers.R
import com.astalavista.pscquestionpapers.activities.QuestionViewActivity
import com.astalavista.pscquestionpapers.adapter.QuestionYearListAdapter
import com.astalavista.pscquestionpapers.adapter.QuestionsRecentsAdapter
import com.astalavista.pscquestionpapers.adapter.QuestionsYearWiseAdapter
import com.astalavista.pscquestionpapers.models.QuestionPaper
import com.astalavista.pscquestionpapers.retrofit.DataManager
import com.astalavista.pscquestionpapers.retrofit.RetrofitCallBack
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.tool_bar_layout.view.*



public class HomeFragment : Fragment() {

    lateinit var yearListAdapter: QuestionYearListAdapter
    lateinit var yearWisetAdapter: QuestionsYearWiseAdapter
    lateinit var recentsAdapter: QuestionsRecentsAdapter
    lateinit var views: View

    lateinit var yearsList : ArrayList<String>

    lateinit var recentQuestionPaperArray: ArrayList<QuestionPaper>
    lateinit var yearWiseQuestionPaperArray: ArrayList<QuestionPaper>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        views = inflater.inflate(R.layout.fragment_home, null)

        initViews()
        setRecentDatas()
        setYearsList()

        return views
    }

    private fun initViews() {

        views.layoutBack.visibility = View.GONE
        views.txtHead.text = "PSC Question Papers"

        views!!.recyclerVwRecents.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        recentsAdapter = QuestionsRecentsAdapter(ArrayList<QuestionPaper>(), object : QuestionsRecentsAdapter.OnRecentQuestionListener {
            override fun onItemTapped(position: Int) {

                var intent =  Intent(activity, QuestionViewActivity::class.java)
                        intent.putExtra("question_data", recentQuestionPaperArray[position])
                activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
                startActivity(intent)

            }
        })
        views!!.recyclerVwRecents.adapter = recentsAdapter


        views!!.recyclerYearList.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        yearListAdapter = QuestionYearListAdapter(
                this.activity!!, ArrayList<String>(),
                object : QuestionYearListAdapter.OnYearClickListener {
                    override fun onYearTapped(position: Int) {
                        super.onYearTapped(position)
                        setYearsListDatas(yearsList[position])
                    }
                }
        )
        views!!.recyclerYearList.adapter = yearListAdapter


        views!!.recyclerVwYearWise.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        yearWisetAdapter = QuestionsYearWiseAdapter(ArrayList<QuestionPaper>(),object :
            QuestionsYearWiseAdapter.OnYearWiseQuestionListener{
            override fun onItemTapped(position: Int) {
                startActivity(Intent(activity, QuestionViewActivity::class.java)
                    .putExtra("question_data",yearWiseQuestionPaperArray.get(position)))
            }

        })
        views!!.recyclerVwYearWise.adapter = yearWisetAdapter

    }

    private fun setRecentDatas() {

        views.progressRecents.visibility = View.VISIBLE
        DataManager.getDatamanager().getRecentQuestions(object : RetrofitCallBack<QuestionPaper> {
            override fun Success(data: QuestionPaper) {

                views.progressRecents.visibility = View.GONE
                recentQuestionPaperArray = data.questionArray
                recentsAdapter.update(recentQuestionPaperArray)

            }
            override fun Failure(data: String) {

            }

        })
    }

    private fun setYearsList() {

        DataManager.getDatamanager().getYearsList(object : RetrofitCallBack<ArrayList<String>> {
            override fun Success(data: ArrayList<String>) {
                yearsList = data
                yearListAdapter.update(data)

                setYearsListDatas(yearsList[0])
            }
            override fun Failure(data: String) {

            }

        })
    }

    private fun setYearsListDatas(year : String) {

        views.recyclerVwYearWise.visibility = View.GONE
        views.progressYearWise.visibility = View.VISIBLE

        DataManager.getDatamanager().getYearsWiseData(year+".txt", object : RetrofitCallBack<QuestionPaper> {
            override fun Success(data: QuestionPaper) {

                views.progressYearWise.visibility = View.GONE
                views.recyclerVwYearWise.visibility = View.VISIBLE

                yearWiseQuestionPaperArray = data.questionArray
                yearWisetAdapter.update(yearWiseQuestionPaperArray)
            }

            override fun Failure(data: String) {

                Toast.makeText(activity,data,Toast.LENGTH_SHORT).show()
                views.progressYearWise.visibility = View.GONE

            }

        })
    }

}
