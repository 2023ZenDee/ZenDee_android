package com.ggd.zendee.feature.map

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ggd.model.Issue.IssueModel
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseListAdapter
import com.ggd.zendee.databinding.ItemIssueDialogBinding
import com.ggd.zendee.feature.login.LoginViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

class IssueDialogPagerAdapter : BaseListAdapter<IssueModel,ItemIssueDialogBinding>(R.layout.item_issue_dialog) {

    override fun action(data: IssueModel, binding: ItemIssueDialogBinding) {
        binding.titleTxt.text = data.title
        binding.locationTxt.text = data.address ?: "위치 정보 없음"

        binding.viewTxt.text = "조회수 ${data.views}회"
        binding.commentTxt.text = "댓글 ${data.comments}개"

        var time = ""

        try{ time = timeToString(data.created_at) }
        catch (e : DateTimeParseException){
            time = "시간을 불러오지 못했습니다"
        }

        binding.timeTxt.text = time


        binding.root.setOnClickListener{
            dialogClickListener.onClick(data)
        }
    }

    lateinit var dialogClickListener : OnItemClickListener

    interface OnItemClickListener{
        fun onClick(issue: IssueModel)
    }

    fun setOnDialogClickListener(onDialogClickListener: OnItemClickListener){
        dialogClickListener = onDialogClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(ItemIssueDialogBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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
}