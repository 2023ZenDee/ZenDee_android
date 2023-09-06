package com.ggd.zendee.feature.issue

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentIssueBinding
import com.ggd.zendee.feature.main.MainActivity
import com.ggd.zendee.feature.main.MainViewModel
import com.ggd.zendee.feature.map.MapViewModel
import com.ggd.zendee.utils.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IssueFragment : BaseFragment<FragmentIssueBinding,IssueViewModel>(R.layout.fragment_issue) {

    override val viewModel : IssueViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val commentDataSet = arrayListOf<CommentData>().apply {
        add(CommentData("르브론 제임스","come on Jimmy!"))
        add(CommentData("유도니스 하슬렘","아이유 어디갓어"))
        add(CommentData("아이유","나 여깄어"))
        add(CommentData("악질 팬","ㄱ짓말 치지 마라"))
    }

    private val commentAdapter : CommentRecyclerviewAdapter by lazy { CommentRecyclerviewAdapter() }

    override fun start() {

        repeatOnStarted {
            viewModel.eventFlow.collect{ event -> handleEvent(event) }
        }

        (activity as MainActivity).handleBottomNavigation(false)

        commentAdapter.submitList(commentDataSet.toMutableList())

        binding.commentRecyclerview.apply {

            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = commentAdapter
        }

        binding.titleTxt.text = mainViewModel.issue.title
        binding.contentTxt.text = mainViewModel.issue.content
//        binding.commentTxt.text = "댓글 ${mainViewModel.issue.}"
        binding.viewTxt.text = "조회수 ${mainViewModel.issue.views}회"



        binding.likeBtn.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                binding.dislikeBtn.isChecked = false
                binding.likeTxt.isChecked = true
                binding.dislikeTxt.isChecked = false
            }
            else binding.likeTxt.isChecked=false
        }

        binding.dislikeBtn.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                binding.likeBtn.isChecked = false
                binding.dislikeTxt.isChecked = true
                binding.likeTxt.isChecked = false
            }
            else binding.dislikeTxt.isChecked=false
        }

        binding.backBtn.setOnClickListener{
            findNavController().popBackStack()
        }

        commentAdapter.submitList(commentDataSet.toMutableList())

    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).handleBottomNavigation(true)

    }

    private fun handleEvent(event: IssueViewModel.Event) =
        when (event) {
            is IssueViewModel.Event.UnknownException -> {}
        }


}