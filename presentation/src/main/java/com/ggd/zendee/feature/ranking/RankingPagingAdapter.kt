package com.ggd.zendee.feature.ranking

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ggd.model.Issue.IssueModel
import com.ggd.zendee.R
import com.ggd.zendee.databinding.ItemRankingBinding
import com.ggd.zendee.feature.login.LoginViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

class RankingPagingAdapter : PagingDataAdapter<IssueModel,RankingPagingAdapter.RankingViewHolder>(RankingItemDiffCallback()) {

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null){
            holder.bind(item)
            holder.itemView.setOnClickListener {
                dialogClickListener.onClick(item)
            }
        }
    }

    private lateinit var dialogClickListener: OnDialogClickListener

    interface OnDialogClickListener{
        fun onClick(issue : IssueModel)
    }
    fun setDialogClickListener(onDialogClickListener: OnDialogClickListener){
        this.dialogClickListener = onDialogClickListener
    }

    fun onDialogClick(data : IssueModel){
        dialogClickListener.onClick(data)
    }
    class RankingViewHolder(private val binding: ItemRankingBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: IssueModel) {
            binding.titleTxt.text = data.title
            binding.addressTxt.text = data.address
            binding.timeTxt.text = timeToString(data.created_at)
            binding.viewsTxt.text = "조회수 ${data.views}회"
            binding.commentsTxt.text = "댓글 ${data.comments}개"

            fun getDrawableByTag(tag : String, context: Context) : Drawable? {

                when(tag){
//                    "뜨거움" -> return context.getDrawable(R.drawable.hot_tag)
//                    "경고" -> return context.getDrawable(R.drawable.alert_tag)
//                    "재미" -> return context.getDrawable(R.drawable.happy_tag)
//                    "공지" -> return context.getDrawable(R.drawable.notice_tag)
//                    "활동" -> return context.getDrawable(R.drawable.active_tag)
//                    "사랑" -> return context.getDrawable(R.drawable.love_tag)
//                    "행운" -> return context.getDrawable(R.drawable.lucky_tag)

                    "뜨거움" -> return binding.root.context.getDrawable(R.drawable.tag_new_hot)
                    "경고" -> return binding.root.context.getDrawable(R.drawable.tag_new_alert)
                    "재미" -> return binding.root.context.getDrawable(R.drawable.tag_new_happy)
                    "공지" -> return binding.root.context.getDrawable(R.drawable.tag_new_notice)
                    "활동" -> return binding.root.context.getDrawable(R.drawable.tag_new_active)
                    "사랑" -> return binding.root.context.getDrawable(R.drawable.tag_new_love)
                    "행운" -> return binding.root.context.getDrawable(R.drawable.tag_new_lucky)
                }

                return context.getDrawable(R.drawable.lucky_tag)
            }

            binding.tagImg.background = getDrawableByTag(data.tags, context = binding.root.context)
            // 다른 데이터를 바인딩하거나 UI 업데이트를 수행
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val binding = ItemRankingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RankingViewHolder(binding)
    }

}

class RankingItemDiffCallback : DiffUtil.ItemCallback<IssueModel>() {
    override fun areItemsTheSame(oldItem: IssueModel, newItem: IssueModel): Boolean {
        return oldItem.postIdx == newItem.postIdx
    }

    override fun areContentsTheSame(oldItem: IssueModel, newItem: IssueModel): Boolean {
        return oldItem == newItem
    }
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