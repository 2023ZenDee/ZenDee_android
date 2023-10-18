package com.ggd.zendee.feature.profile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ProfileTabAdapter(
    fm: FragmentManager
): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        TODO("Not yet implemented")
    }

    override fun getPageTitle(position: Int): CharSequence {
        val title = when(position) {
            0 -> "좋아요"
            1 -> "싫어요"
            2 -> "이슈"
            3 -> "댓글"
            else -> "좋아요"
        }
        return title
    }

    override fun getCount(): Int {
        val viewCount = 4
        return viewCount
    }


}