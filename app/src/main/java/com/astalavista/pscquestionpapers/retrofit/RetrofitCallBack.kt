package com.astalavista.pscquestionpapers.retrofit

interface RetrofitCallBack<T> {

    fun Success(data : T)
    fun Failure(data : String)

}