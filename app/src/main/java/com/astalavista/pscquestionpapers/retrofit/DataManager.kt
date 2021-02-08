package com.astalavista.pscquestionpapers.retrofit

import android.content.Context
import android.view.MotionEvent
import com.astalavista.pscquestionpapers.models.QuestionPaper
import com.astalavista.pscquestionpapers.models.ResponseResult
import com.astalavista.psc_kerala.Retrofit.PscApiClient
import com.astalavista.pscquestionpapers.models.QuestionHead
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataManager {

    private var dataManger: DataManager? = null
    private var pscApiInterface: PscApiInterface? = null
    private var mContext: Context? = null

    fun getDatamanager(): DataManager {
        if (dataManger == null)
            dataManger = DataManager

        return dataManger as DataManager
    }

    fun init(applicationContext: Context) {
        pscApiInterface = PscApiClient.aPiClient!!.create(
            PscApiInterface::class.java
        )
        mContext = applicationContext

    }

    public fun getRecentQuestions(retrofitCallBack: RetrofitCallBack<QuestionPaper>) {

        val resultCall: Call<ResponseResult<QuestionPaper>> = pscApiInterface!!.getRecentQuestions()
        resultCall.enqueue(object : Callback<ResponseResult<QuestionPaper>> {
            override fun onResponse(
                call: Call<ResponseResult<QuestionPaper>>?,
                response: Response<ResponseResult<QuestionPaper>>?
            ) {
                retrofitCallBack.Success(response!!.body().data)
            }

            override fun onFailure(call: Call<ResponseResult<QuestionPaper>>?, t: Throwable?) {
                retrofitCallBack.Failure("Error")
            }
        })
    }

    public fun getYearsList(retrofitCallBack: RetrofitCallBack<ArrayList<String>>) {

        val resultCall: Call<ResponseResult<ArrayList<String>>> = pscApiInterface!!.getYearsList()
        resultCall.enqueue(object : Callback<ResponseResult<ArrayList<String>>> {
            override fun onResponse(
                call: Call<ResponseResult<ArrayList<String>>>?,
                response: Response<ResponseResult<ArrayList<String>>>?
            ) {
                retrofitCallBack.Success(response!!.body().data)
            }

            override fun onFailure(call: Call<ResponseResult<ArrayList<String>>>?, t: Throwable?) {
                retrofitCallBack.Failure("Error")
            }
        })
    }

    public fun getYearsWiseData(urldata : String,retrofitCallBack: RetrofitCallBack<QuestionPaper>) {

        val resultCall: Call<ResponseResult<QuestionPaper>> = pscApiInterface!!.getYearsWiseData(urldata)
        resultCall.enqueue(object : Callback<ResponseResult<QuestionPaper>> {
            override fun onResponse(
                call: Call<ResponseResult<QuestionPaper>>?,
                response: Response<ResponseResult<QuestionPaper>>?
            ) {
                retrofitCallBack.Success(response!!.body().data)
            }

            override fun onFailure(call: Call<ResponseResult<QuestionPaper>>?, t: Throwable?) {
                retrofitCallBack.Failure("Error")
            }
        })
    }

    public fun getSearchingData(retrofitCallBack: RetrofitCallBack<QuestionPaper>) {

        val resultCall: Call<ResponseResult<QuestionPaper>> = pscApiInterface!!.getSearchingData()
        resultCall.enqueue(object : Callback<ResponseResult<QuestionPaper>> {
            override fun onResponse(
                call: Call<ResponseResult<QuestionPaper>>?,
                response: Response<ResponseResult<QuestionPaper>>?
            ) {
                retrofitCallBack.Success(response!!.body().data)
            }

            override fun onFailure(call: Call<ResponseResult<QuestionPaper>>?, t: Throwable?) {
                retrofitCallBack.Failure("Error")
            }
        })
    }

    public fun getSyllabusData(retrofitCallBack: RetrofitCallBack<QuestionHead>) {

        val resultCall: Call<ResponseResult<QuestionHead>> = pscApiInterface!!.getSyllabusData()
        resultCall.enqueue(object : Callback<ResponseResult<QuestionHead>> {
            override fun onResponse(
                call: Call<ResponseResult<QuestionHead>>?,
                response: Response<ResponseResult<QuestionHead>>?
            ) {
                retrofitCallBack.Success(response!!.body().data)
            }

            override fun onFailure(call: Call<ResponseResult<QuestionHead>>?, t: Throwable?) {
                retrofitCallBack.Failure("Error")
            }
        })
    }

    public fun checkVersion(retrofitCallBack: RetrofitCallBack<String>) {

        val resultCall: Call<ResponseResult<String>> = pscApiInterface!!.checkVersion()
        resultCall.enqueue(object : Callback<ResponseResult<String>> {
            override fun onResponse(
                call: Call<ResponseResult<String>>?,
                response: Response<ResponseResult<String>>?
            ) {
                retrofitCallBack.Success(response!!.body().data)
            }
            override fun onFailure(call: Call<ResponseResult<String>>?, t: Throwable?) {
                retrofitCallBack.Failure("Error")
            }
        })
    }


}