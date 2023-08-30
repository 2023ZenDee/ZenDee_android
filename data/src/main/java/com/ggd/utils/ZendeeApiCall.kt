package com.ggd.utils

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

suspend inline fun <T> zendeeApiCall(
    crossinline callFunction : suspend () -> T
): T {
    return try {
        withContext(Dispatchers.IO){
            callFunction.invoke()
        }
    } catch (e : HttpException){
        val message : String = e.message!!
        Log.d("젠디", "zendeeApiCall: ${message} ")

        throw Exception()
        }
    }