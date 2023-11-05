package com.ggd.zendee.feature.profile.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ContentInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ggd.model.user.ContentData
import com.ggd.zendee.R
import com.ggd.zendee.feature.ranking.RankingItemData
import com.ggd.zendee.utils.timeToString
import okhttp3.internal.immutableListOf

class ProfileListAdapter(
    private val itemList: List<ContentData>,
    private val onItemClickListener: OnItemClickListener
): RecyclerView.Adapter<ProfileListAdapter.ViewHolder>() {

    class ViewHolder(
        view: View,
        private val onItemClickListener: OnItemClickListener
    ): RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        val tagImage: ImageView = view.findViewById(R.id.tag_img)
        val issueTitle: TextView = view.findViewById(R.id.title_txt)
        val issueAddress: TextView = view.findViewById(R.id.address_txt)
        val issueTime: TextView = view.findViewById(R.id.time_txt)
        val issueViews: TextView = view.findViewById(R.id.views_txt)
        val issueComments: TextView = view.findViewById(R.id.comments_txt)

        override fun onClick(v: View?) {
            onItemClickListener.onItemClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_issue, parent, false)

        return ViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        val tag: Int = when(item.tags){
            "뜨거움" -> R.drawable.hot_tag
            "경고" -> R.drawable.alert_tag
            "재미" -> R.drawable.happy_tag
            "공지" -> R.drawable.notice_tag
            "활동" -> R.drawable.active_tag
            "사랑" -> R.drawable.love_tag
            "행운" -> R.drawable.lucky_tag
            else -> 0
        }

        holder.tagImage.setImageResource(tag)
        holder.issueTitle.text = item.title
        holder.issueAddress.text = item.address
        holder.issueTime.text = timeToString(item.created_at)
        holder.issueViews.text = "조회수 " + item.views + "회"
//        holder.issueComments.text = "댓글 " + item.comments // todo : 수정
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}