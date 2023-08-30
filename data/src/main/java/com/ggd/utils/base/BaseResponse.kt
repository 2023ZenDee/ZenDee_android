package com.ggd.utils.base

data class BaseResponse<T>(

    val status : Int,
    val success : Boolean,
    val message : String,
    val data : T?

)