package com.ggd.zendee.feature.ranking

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ggd.model.Issue.IssueModel
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseListAdapter
import com.ggd.zendee.databinding.ItemRankingBinding
import com.ggd.zendee.databinding.ItemTagBinding
import com.ggd.zendee.feature.login.LoginViewModel
import com.ggd.zendee.utils.timeToString
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

class RankingRecyclerviewAdapter(context : Context) : BaseListAdapter<IssueModel, ItemRankingBinding>(R.layout.item_ranking){

    override fun action(data: IssueModel, binding: ItemRankingBinding) {
        binding.titleTxt.text = data.title
        binding.addressTxt.text = data.address
        binding.timeTxt.text = timeToString(data.created_at)
        binding.viewsTxt.text = "조회수 ${data.views}회"
        binding.commentsTxt.text = "댓글 ${data.comments}개"

        binding.root.setOnClickListener{

            dialogClickListener.onClick(data)
        }

        fun getDrawableByTag(tag : String, context: Context) : Drawable? {

            when(tag){
                "뜨거움" -> return context.getDrawable(R.drawable.hot_tag)
                "경고" -> return context.getDrawable(R.drawable.alert_tag)
                "재미" -> return context.getDrawable(R.drawable.happy_tag)
                "공지" -> return context.getDrawable(R.drawable.notice_tag)
                "활동" -> return context.getDrawable(R.drawable.active_tag)
                "사랑" -> return context.getDrawable(R.drawable.love_tag)
                "행운" -> return context.getDrawable(R.drawable.lucky_tag)
            }

            return context.getDrawable(R.drawable.lucky_tag)
        }

        binding.tagImg.background = getDrawableByTag(data.tags, context = binding.root.context)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(ItemRankingBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    private lateinit var dialogClickListener: OnDialogClickListener
    interface OnDialogClickListener{
        fun onClick(issue : IssueModel)
    }
    fun setDialogClickListener(onDialogClickListener: OnDialogClickListener){
        this.dialogClickListener = onDialogClickListener
    }

}

