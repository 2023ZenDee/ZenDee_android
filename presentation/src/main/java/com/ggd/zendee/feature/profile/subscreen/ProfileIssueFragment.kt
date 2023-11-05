package com.ggd.zendee.feature.profile.subscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggd.model.Issue.IssueModel
import com.ggd.model.user.ContentData
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentProfileIssueBinding
import com.ggd.zendee.databinding.FragmentProfileLikeBinding
import com.ggd.zendee.feature.main.MainViewModel
import com.ggd.zendee.feature.profile.adapter.ProfileListAdapter
import com.ggd.zendee.feature.profile.screen.ProfileFragmentDirections
import com.ggd.zendee.feature.profile.viewmodel.ProfileViewModel
import com.ggd.zendee.feature.ranking.RankingItemData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileIssueFragment: Fragment(), ProfileListAdapter.OnItemClickListener {

    private val viewModel: ProfileViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    lateinit var binding: FragmentProfileIssueBinding
    private val issueList = mutableListOf<ContentData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_issue, container, false)

        viewModel.getMyIssueContent()

        viewModel.myIssueList.observe(viewLifecycleOwner) {
            setIssueList(it)
        }

        return binding.root
    }

    private fun setIssueList(contentList: List<ContentData>) {
        issueList.removeAll(issueList)
        contentList.forEach { contentData ->
            issueList.add(contentData)
        }
        val adapter = ProfileListAdapter(issueList, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val item = issueList[position]

        val clickedIssue = IssueModel(
            item.postIdx,
            item.title,
            item.content,
            item.created_at,
            item.updated_at,
            item.latitude,
            item.longitude,
            item.deleted_at,
            item.authorIdx,
            item.postImg,
            item.views,
            item.tags,
            item.user,
            item.likes,
            item.bads,
            item.comments,
            item.address,
            item.userLikesPost,
        )

        mainViewModel.isNavigate = true

        if(!mainViewModel.isCancelled){
            mainViewModel.timer.cancel()
            mainViewModel.timerTask.cancel()

            mainViewModel.isCancelled = !mainViewModel.isCancelled
        }

        mainViewModel.issue = clickedIssue

        val action = ProfileFragmentDirections.toIssueFragment()
        findNavController().navigate(action)
    }
}