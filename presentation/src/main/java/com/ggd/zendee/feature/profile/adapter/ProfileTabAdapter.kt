package com.ggd.zendee.feature.profile.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ggd.zendee.feature.profile.subscreen.ProfileCommentFragment
import com.ggd.zendee.feature.profile.subscreen.ProfileIssueFragment
import com.ggd.zendee.feature.profile.subscreen.ProfileLikeFragment
import com.ggd.zendee.feature.profile.subscreen.ProfileUnLikeFragment

class ProfileTabAdapter(
    fragmentManager: FragmentManager, lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        val fragment = when(position) {
            0 -> ProfileLikeFragment()
            1 -> ProfileUnLikeFragment()
            2 -> ProfileIssueFragment()
            3 -> ProfileCommentFragment()
            else -> ProfileLikeFragment()
        }
        return fragment
    }

    override fun getItemCount(): Int {
        val viewCount = 4
        return viewCount
    }
}