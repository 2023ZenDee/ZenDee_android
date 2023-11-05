package com.ggd.zendee.feature.map

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseListAdapter
import com.ggd.zendee.databinding.ItemCommentBinding
import com.ggd.zendee.databinding.ItemTagBinding
import com.naver.maps.map.overlay.OverlayImage

class TagRecyclerviewAdapter : BaseListAdapter<IssueTag,ItemTagBinding> (R.layout.item_tag){

    val dataSize = 7

    override fun action(data: IssueTag, binding: ItemTagBinding) {

        when(data){
//            IssueTag.HOT -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.hot_tag)
//            IssueTag.ALERT -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.alert_tag)
//            IssueTag.HAPPY -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.happy_tag)
//            IssueTag.NOTICE -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.notice_tag)
//            IssueTag.ACTIVE -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.active_tag)
//            IssueTag.LOVE -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.love_tag)
//            IssueTag.LUCKY -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.lucky_tag)

            IssueTag.HOT -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.tag_new_hot)
            IssueTag.ALERT -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.tag_new_alert)
            IssueTag.HAPPY -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.tag_new_happy)
            IssueTag.NOTICE -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.tag_new_notice)
            IssueTag.ACTIVE -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.tag_new_active)
            IssueTag.LOVE -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.tag_new_love)
            IssueTag.LUCKY -> binding.tagCheckbox.background = binding.root.context.getDrawable(R.drawable.tag_new_lucky)
        }

    }

    override fun getItem(position: Int): IssueTag {
        return super.getItem(position % dataSize )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(ItemTagBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

}