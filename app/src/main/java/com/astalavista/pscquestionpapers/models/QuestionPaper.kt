package com.astalavista.pscquestionpapers.models;

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class QuestionPaper(
    val id: String,
    val head: String,
    val name: String,
    val date: String,
    val questionUrl: String,
    val answerUrl: String,
    val type: String,
    @SerializedName("recent_question_papers") val questionArray: ArrayList<QuestionPaper>,
    @SerializedName("search_question_papers")val searchQuestionData : ArrayList<QuestionPaper>,
    var answerArray : ArrayList<String>
) : Serializable {


}
