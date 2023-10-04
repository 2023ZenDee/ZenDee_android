package com.ggd.zendee.feature.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ggd.model.Issue.IssueModel
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseListAdapter
import com.ggd.zendee.databinding.ItemRankingBinding
import com.ggd.zendee.databinding.ItemTagBinding

class RankingRecyclerviewAdapter : BaseListAdapter<IssueModel, ItemRankingBinding>(R.layout.item_ranking){

    override fun action(data: IssueModel, binding: ItemRankingBinding) {
        binding.titleTxt.text = data.title
        binding.addressTxt.text = data.latitude.toString()
        binding.timeTxt.text = data.created_at
        binding.viewsTxt.text = "조회수 ${data.views}회"

        binding.root.setOnClickListener{

            dialogClickListener.onClick(data)
        }

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