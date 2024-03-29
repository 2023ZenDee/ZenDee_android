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
import com.ggd.zendee.databinding.FragmentProfileBinding
import com.ggd.zendee.databinding.FragmentProfileLikeBinding
import com.ggd.zendee.feature.main.MainViewModel
import com.ggd.zendee.feature.profile.adapter.ProfileListAdapter
import com.ggd.zendee.feature.profile.screen.ProfileFragmentDirections
import com.ggd.zendee.feature.profile.viewmodel.ProfileViewModel
import com.ggd.zendee.feature.ranking.RankingItemData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileLikeFragment: Fragment(), ProfileListAdapter.OnItemClickListener {

    private val likeList = mutableListOf<ContentData>()

    private val viewModel: ProfileViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    lateinit var binding: FragmentProfileLikeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_like, container, false)

        viewModel.getMyLikeContent()

        viewModel.myLikeList.observe(viewLifecycleOwner) {
            setLikeList(it)
        }

        return binding.root
    }

    private fun setLikeList(contentList: List<ContentData>) {
        likeList.removeAll(likeList)
        contentList.forEach { contentData ->
            likeList.add(contentData)
        }
        val adapter = ProfileListAdapter(likeList, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val item = likeList[position]

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