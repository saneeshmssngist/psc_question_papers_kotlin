package com.astalavista.pscquestionpapers.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.astalavista.pscquestionpapers.R
import com.astalavista.pscquestionpapers.activities.QuestionViewActivity
import com.astalavista.pscquestionpapers.adapter.SearchAdapter
import com.astalavista.pscquestionpapers.models.QuestionPaper
import com.astalavista.pscquestionpapers.retrofit.DataManager
import com.astalavista.pscquestionpapers.retrofit.RetrofitCallBack
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    lateinit var views: View
    lateinit var searchAdapter: SearchAdapter
    var questionArray: ArrayList<QuestionPaper> = ArrayList<QuestionPaper>()
    var searchArray: ArrayList<QuestionPaper> = ArrayList<QuestionPaper>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        views = inflater.inflate(R.layout.fragment_search, container, false)

        initViews()
        initControls()
        setDatas()

        return views
    }

    private fun initControls() {

        views.edtTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                if (edtTextSearch.text.toString().isEmpty()) {

                    searchArray = ArrayList<QuestionPaper>()
                    searchAdapter.update(questionArray)
                    searchAdapter.notifyDataSetChanged()

                } else {

                    if (questionArray.size > 0)
                        applyFilter(edtTextSearch.text.toString())

                }
            }

        })
    }

    private fun applyFilter(searchData: String) {

        var searchingArray: ArrayList<QuestionPaper> = ArrayList<QuestionPaper>()
        for (questionPaper in questionArray) {
            if (questionPaper.name.toLowerCase().contains(searchData.toLowerCase())) {
                searchingArray.add(questionPaper)
            }
        }

        searchArray = searchingArray
        searchAdapter.update(searchArray)
        searchAdapter.notifyDataSetChanged()
    }

    private fun initViews() {

        views.recyclerViewSearch.layoutManager = LinearLayoutManager(activity)
        searchAdapter =
            SearchAdapter(ArrayList<QuestionPaper>(), object : SearchAdapter.OnSearchListener {
                override fun onSearchTapped(position: Int) {

                    if (searchArray.size > 0)
                        startActivity(
                            Intent(activity, QuestionViewActivity::class.java)
                                .putExtra("question_data", searchArray[position])
                        )
                    else
                        startActivity(
                            Intent(activity, QuestionViewActivity::class.java)
                                .putExtra("question_data", questionArray[position])
                        )
                }
            })
        views.recyclerViewSearch.adapter = searchAdapter

    }

    private fun setDatas() {

        views.progressSearch.visibility = View.VISIBLE

        DataManager.getDatamanager().getSearchingData(object : RetrofitCallBack<QuestionPaper> {
            override fun Success(data: QuestionPaper) {

                views.progressSearch.visibility = View.GONE

                questionArray = data.searchQuestionData
                searchAdapter.update(data.searchQuestionData)
            }

            override fun Failure(data: String) {

            }

        })

    }

}