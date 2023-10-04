package com.ggd.zendee.feature.map

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider.OnChangeListener

class ZoomCenterItemScrollListener(private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        var centerItemPosition = 0

        if(dx>1){
            centerItemPosition = firstVisibleItemPosition + visibleItemCount / 2

        }else {
            centerItemPosition = lastVisibleItemPosition - visibleItemCount / 2
        }

        changedListener.onChanged(centerItemPosition)

        for (i in 0 until visibleItemCount) {
            val child = recyclerView.getChildAt(i)
            var childPosition = recyclerView.getChildAdapterPosition(child)

            if (childPosition != centerItemPosition){
                child.scaleX = Math.max(0.7F, 0.5f)
                child.scaleY = Math.max(0.7F, 0.5f)
            }else{
                child.scaleX = Math.max(1F, 0.5f)
                child.scaleY = Math.max(1F, 0.5f)
            }

        }

    }

    interface OnChangedListener{
        fun onChanged(position : Int)

    }

    private lateinit var changedListener : OnChangedListener

    fun setChangedListener(onChangedListener: OnChangedListener){
        this.changedListener = onChangedListener
    }

}