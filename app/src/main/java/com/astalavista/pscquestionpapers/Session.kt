package com.astalavista.pscquestionpapers;

import android.content.Context
import android.content.SharedPreferences
import com.astalavista.pscquestionpapers.models.QuestionPaper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Session {

    private val PREFERENCE = "PREFERENCE"

    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    public fun getSession(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }

    public fun getData(): String {
        return sharedPreferences!!.getString("favourite_data", "[]")
    }

    public fun addData(data: String) {
        editor!!.putString("favourite_data", data).commit()
    }

    public fun getArrayData(): ArrayList<QuestionPaper> {
        return Gson().fromJson(getData(), object : TypeToken<ArrayList<QuestionPaper>>() {}.type)
    }

    public fun addQuestion(question: QuestionPaper) {
        var questionData = getArrayData()
        questionData.add(question)
        addData(Gson().toJson(questionData))
    }

    public fun removeQuestion(question: QuestionPaper) {
        var questionData = getArrayData()
        var questionDataDup = getArrayData()

        for(ques in questionDataDup)
        {
            if(ques.id.contentEquals(question.id))
                questionData.remove(ques)
        }
        addData(Gson().toJson(questionData))
    }

    public fun getCounter(): Int {
        return sharedPreferences!!.getInt("get_counter", 0)
    }

    public fun setCounter() {
        editor!!.putInt("get_counter", getCounter()+1).commit()
    }


}
