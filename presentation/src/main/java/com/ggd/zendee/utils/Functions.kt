package com.ggd.zendee.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.ggd.zendee.feature.login.LoginViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

fun makeToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun timeToString(time : String) : String{

    try{
        val changedUpdatedAt = time.replace("Z", "")
        val createdTime =
            LocalDateTime.parse(changedUpdatedAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                .plusHours(9)

        val currentTime = LocalDateTime.now()
        val secondDiff = ChronoUnit.SECONDS.between(createdTime, currentTime).toInt()
        val minDiff = ChronoUnit.MINUTES.between(createdTime, currentTime).toInt()
        val hourDiff = ChronoUnit.HOURS.between(createdTime, currentTime).toInt()
        val dayDiff = ChronoUnit.DAYS.between(createdTime, currentTime).toInt()
        val monthDiff = ChronoUnit.MONTHS.between(createdTime, currentTime).toInt()
        val yearDiff = ChronoUnit.YEARS.between(createdTime, currentTime).toInt()
        Log.d(LoginViewModel.TAG, "timeDiff - ${minDiff} ${hourDiff} ${dayDiff} ${monthDiff} ${yearDiff}")

        if (minDiff == 0) return "${secondDiff}초 전"
        else if (hourDiff == 0) return "${minDiff}분 전"
        else if (dayDiff == 0) return "${hourDiff}시간 전"
        else if (monthDiff == 0) return "${dayDiff}일 전"
        else if (yearDiff == 0) return "${monthDiff}달 전"
        else return "0초 전"
    }catch (e : DateTimeParseException){
        throw e
    }

}