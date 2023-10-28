package com.ggd.zendee.feature.profile.subscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggd.model.user.ContentData
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentProfileIssueBinding
import com.ggd.zendee.databinding.FragmentProfileLikeBinding
import com.ggd.zendee.feature.profile.adapter.ProfileListAdapter
import com.ggd.zendee.feature.profile.viewmodel.ProfileViewModel
import com.ggd.zendee.feature.ranking.RankingItemData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileIssueFragment: Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private val issueList = mutableListOf<ContentData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding: FragmentProfileIssueBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_issue, container, false)

        viewModel.getMyIssueContent()

        viewModel.myLikeList.observe(viewLifecycleOwner) {
            issueList.removeAll(issueList)
            it.forEach { contentData ->
                issueList.add(contentData)
            }
            val adapter = ProfileListAdapter(requireContext(), issueList)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = adapter
        }

        return binding.root
    }
}