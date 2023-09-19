package com.ggd.zendee.feature.issue

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ggd.model.Issue.CommentModel
import com.ggd.utils.BASE_URL
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseListAdapter
import com.ggd.zendee.databinding.ItemCommentBinding

class CommentRecyclerviewAdapter : BaseListAdapter<CommentModel,ItemCommentBinding>(R.layout.item_comment) {

    override fun action(data: CommentModel, binding: ItemCommentBinding) {
        binding.commentTxt.text = data.cmtContent
        binding.profileTxt.text = data.user
        Glide.with(binding.root.context).load(BASE_URL + data.userImg).into(binding.profileImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

}