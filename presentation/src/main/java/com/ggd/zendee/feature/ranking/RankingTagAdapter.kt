package com.ggd.zendee.feature.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseListAdapter
import com.ggd.zendee.databinding.ItemIssueDialogBinding
import com.ggd.zendee.databinding.ItemRankingTagBinding

class RankingTagAdapter : BaseListAdapter<RankingTagData,ItemRankingTagBinding>(R.layout.item_ranking_tag) {

    override fun action(data: RankingTagData, binding: ItemRankingTagBinding) {
        binding.tagCheckbox.background = data.tagDrawble

        binding.tagCheckbox.setOnCheckedChangeListener { compoundButton, b ->

            


        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(ItemRankingTagBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}