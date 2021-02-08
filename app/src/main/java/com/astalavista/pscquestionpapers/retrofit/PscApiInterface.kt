package com.astalavista.pscquestionpapers.retrofit

import com.astalavista.pscquestionpapers.models.QuestionHead
import com.astalavista.pscquestionpapers.models.QuestionPaper
import com.astalavista.pscquestionpapers.models.ResponseResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PscApiInterface {


    @GET("psc_recent.txt")
    fun getRecentQuestions(): Call<ResponseResult<QuestionPaper>>

    @GET("years_list.txt")
    fun getYearsList(): Call<ResponseResult<ArrayList<String>>>

    @GET("{urlData}")
    fun getYearsWiseData(@Path(value = "urlData") userId: String): Call<ResponseResult<QuestionPaper>>

    @GET("search_psc.txt")
    fun getSearchingData(): Call<ResponseResult<QuestionPaper>>

    @GET("syllabus_data.txt")
    fun getSyllabusData(): Call<ResponseResult<QuestionHead>>

    @GET("version_check.txt")
    fun checkVersion(): Call<ResponseResult<String>>


}