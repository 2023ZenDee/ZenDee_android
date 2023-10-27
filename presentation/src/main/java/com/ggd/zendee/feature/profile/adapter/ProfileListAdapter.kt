package com.ggd.zendee.feature.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ggd.zendee.R
import com.ggd.zendee.feature.ranking.RankingItemData
import okhttp3.internal.immutableListOf

class ProfileListAdapter(
    private val context: Context,
    private val itemList: List<RankingItemData>
): RecyclerView.Adapter<ProfileListAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tagImage: ImageView = view.findViewById(R.id.tag_img)
        val issueTitle: TextView = view.findViewById(R.id.title_txt)
        val issueAddress: TextView = view.findViewById(R.id.address_txt)
        val issueTime: TextView = view.findViewById(R.id.time_txt)
        val issueViews: TextView = view.findViewById(R.id.views_txt)
        val issueComments: TextView = view.findViewById(R.id.comments_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ranking, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        Glide.with(context).load(item.tag).into(holder.tagImage)
        holder.issueTitle.text = item.title
        holder.issueAddress.text = item.address
        holder.issueTime.text = item.time
        holder.issueViews.text = "조회수 " + item.views + "회"
        holder.issueComments.text = "댓글 " + item.comments
    }
}