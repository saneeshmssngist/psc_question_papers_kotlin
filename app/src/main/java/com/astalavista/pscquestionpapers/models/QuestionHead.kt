package com.astalavista.pscquestionpapers.models

import com.google.gson.annotations.SerializedName

data class QuestionHead(
    val id: String, @SerializedName("syllabus_name") val syllabusName: String,
    @SerializedName("question_syllabus_heads") val arrayListHeads: ArrayList<QuestionHead>,
    @SerializedName("question_syllabus") val arrayListData: ArrayList<QuestionPaper>
) {
}