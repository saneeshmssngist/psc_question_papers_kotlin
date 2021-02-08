package com.astalavista.pscquestionpapers.models

data class ResponseResult<T>(val code: String, val status: String, val data: T) {
}