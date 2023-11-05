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
import com.ggd.zendee.databinding.FragmentProfileCommentBinding
import com.ggd.zendee.databinding.FragmentProfileLikeBinding
import com.ggd.zendee.feature.profile.adapter.ProfileListAdapter
import com.ggd.zendee.feature.profile.viewmodel.ProfileViewModel
import com.ggd.zendee.feature.ranking.RankingItemData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileCommentFragment: Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    lateinit var binding: FragmentProfileCommentBinding

    private val commentList = mutableListOf<ContentData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_comment, container, false)

        viewModel.getMyCommentContent()

        viewModel.myCommentList.observe(viewLifecycleOwner) {
            setCommentList(it)
        }

        return binding.root
    }

    private fun setCommentList(contentList: List<ContentData>) {
        commentList.removeAll(commentList)
        contentList.forEach { contentData ->
            commentList.add(contentData)
        }
        val adapter = ProfileListAdapter(commentList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }
}