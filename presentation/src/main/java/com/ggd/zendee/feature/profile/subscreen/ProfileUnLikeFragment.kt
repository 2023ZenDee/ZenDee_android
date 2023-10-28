package com.ggd.zendee.feature.profile.subscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggd.model.user.ContentData
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentProfileLikeBinding
import com.ggd.zendee.databinding.FragmentProfileUnLikeBinding
import com.ggd.zendee.feature.profile.adapter.ProfileListAdapter
import com.ggd.zendee.feature.ranking.RankingItemData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileUnLikeFragment: Fragment() {

    private val unLikeList = mutableListOf<ContentData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding: FragmentProfileUnLikeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_un_like, container, false)

        val adapter = ProfileListAdapter(requireContext(), unLikeList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        return binding.root
    }
}