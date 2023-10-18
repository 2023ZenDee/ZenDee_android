package com.ggd.zendee.feature.profile.subscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentProfileBinding
import com.ggd.zendee.databinding.FragmentProfileLikeBinding
import com.ggd.zendee.feature.profile.adapter.ProfileListAdapter
import com.ggd.zendee.feature.ranking.RankingItemData

class ProfileLikeFragment: Fragment() {

    private val likeList = listOf(
        RankingItemData("", "title", "address", "time", 100, 100),
        RankingItemData("", "title", "address", "time", 100, 100),
        RankingItemData("", "title", "address", "time", 100, 100),
        RankingItemData("", "title", "address", "time", 100, 100),
        RankingItemData("", "title", "address", "time", 100, 100),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding: FragmentProfileLikeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_like, container, false)

        val adapter = ProfileListAdapter(requireContext(), likeList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        return binding.root
    }

}