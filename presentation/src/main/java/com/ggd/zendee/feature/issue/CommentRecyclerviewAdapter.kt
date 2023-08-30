package com.ggd.zendee.feature.issue

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseListAdapter
import com.ggd.zendee.databinding.ItemCommentBinding

class CommentRecyclerviewAdapter : BaseListAdapter<CommentData,ItemCommentBinding>(R.layout.item_comment) {

    override fun action(data: CommentData, binding: ItemCommentBinding) {
        binding.commentTxt.text = data.content
        binding.profileTxt.text = data.userName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


}