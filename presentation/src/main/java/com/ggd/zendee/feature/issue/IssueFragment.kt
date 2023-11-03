package com.ggd.zendee.feature.issue

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ggd.model.Issue.CommentModel
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentIssueBinding
import com.ggd.zendee.di.utils.BASE_URL
import com.ggd.zendee.feature.main.MainActivity
import com.ggd.zendee.feature.main.MainViewModel
import com.ggd.zendee.feature.map.IssueTag
import com.ggd.zendee.feature.map.MapViewModel
import com.ggd.zendee.utils.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

@AndroidEntryPoint
class IssueFragment : BaseFragment<FragmentIssueBinding,IssueViewModel>(R.layout.fragment_issue) {

    override val viewModel : IssueViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    val TAG = "젠디"

    private val commentAdapter : CommentRecyclerviewAdapter by lazy { CommentRecyclerviewAdapter() }

    override fun start() {

        if (mainViewModel.issue.userLikesPost!=null){
            if (mainViewModel.issue.userLikesPost!!){
                binding.likeBtn.isChecked = true
                binding.likeTxt.isChecked = true
            }
            else{
                binding.dislikeBtn.isChecked = true
                binding.dislikeTxt.isChecked = true
            }
        }

        repeatOnStarted {
            viewModel.eventFlow.collect{ event -> handleEvent(event) }
        }

        binding.commentRecyclerview.apply {

            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = commentAdapter
        }

        viewModel.getComments(mainViewModel.issue.postIdx)

        binding.titleTxt.text = mainViewModel.issue.title
        binding.contentTxt.text = mainViewModel.issue.content
        binding.commentTxt.text = "댓글 ${mainViewModel.issue.comments}개"
        binding.viewTxt.text = "조회수 ${mainViewModel.issue.views}회"
        binding.profileTxt.text = mainViewModel.issue.user
        if(mainViewModel.issue.postImg != null) Glide.with(requireContext()).load(BASE_URL + mainViewModel.issue.postImg).into(binding.imageImg)
//        else binding.imageImg.visibility = View.GONE
        binding.tagImg.background = getDrawableByTag(mainViewModel.issue.tags)
        try{ binding.timeTxt.text = timeToString(mainViewModel.issue.created_at) }
        catch (e : DateTimeParseException){binding.timeTxt.text = "시간을 불러오지 못했습니다"}
        binding.locationTxt.text = mainViewModel.issue.address ?: "위치 정보 없음"
        binding.likeTxt.text = mainViewModel.issue.likes.toString()
        binding.dislikeTxt.text = mainViewModel.issue.bads.toString()

        binding.reviewBtn.setOnClickListener {
            viewModel.postComment(id = mainViewModel.issue.postIdx, content = binding.reviewEdittxt.text.toString())
        }

        binding.reviewEdittxt.setOnEditorActionListener{ textView, action, event ->

            var handled = false

            if (action == EditorInfo.IME_ACTION_DONE) {
                // 키보드 내리기
                viewModel.postComment(id = mainViewModel.issue.postIdx, content = binding.reviewEdittxt.text.toString())
                handled = true
            }

            handled
        }

        binding.likeBtn.setOnCheckedChangeListener { compoundButton, b ->

            if(b){
                binding.likeTxt.isChecked = true
                binding.dislikeTxt.isChecked = false
            }
            else binding.likeTxt.isChecked=false

            Log.d(TAG,"like - ${b}")

            if (binding.likeBtn.isEnabled && binding.dislikeBtn.isEnabled) {
                binding.likeBtn.isEnabled = false
                viewModel.postLike(mainViewModel.issue.postIdx, true)
            }

        }

        binding.likeTxt.setOnClickListener {
            binding.likeBtn.isChecked = !binding.likeBtn.isChecked
        }

        binding.dislikeTxt.setOnClickListener {
            binding.dislikeBtn.isChecked = !binding.dislikeBtn.isChecked
        }

        binding.dislikeBtn.setOnCheckedChangeListener { compoundButton, b ->

            if(b){
                binding.dislikeTxt.isChecked = true
                binding.likeTxt.isChecked = false
            }
            else binding.dislikeTxt.isChecked=false

            Log.d(TAG,"dislike - ${b}")

            if(binding.likeBtn.isEnabled && binding.dislikeBtn.isEnabled){
                binding.dislikeBtn.isEnabled = false
                viewModel.postLike(mainViewModel.issue.postIdx, false)
            }

        }

        binding.backBtn.setOnClickListener{
            findNavController().popBackStack()
        }

    }

    private fun getDrawableByTag(tag : String) : Drawable? {

        when(tag){
            "뜨거움" -> return binding.root.context.getDrawable(R.drawable.hot_tag)
            "경고" -> return binding.root.context.getDrawable(R.drawable.alert_tag)
            "재미" -> return binding.root.context.getDrawable(R.drawable.happy_tag)
            "공지" -> return binding.root.context.getDrawable(R.drawable.notice_tag)
            "활동" -> return binding.root.context.getDrawable(R.drawable.active_tag)
            "사랑" -> return binding.root.context.getDrawable(R.drawable.love_tag)
            "행운" -> return binding.root.context.getDrawable(R.drawable.lucky_tag)
        }

        return binding.root.context.getDrawable(R.drawable.lucky_tag)
    }

    fun timeToString(time : String) : String{

        try{
            val changedUpdatedAt = time.replace("Z", "")
            val createdTime =
                LocalDateTime.parse(changedUpdatedAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    .plusHours(9)

            val currentTime = LocalDateTime.now()
            val secondDiff = ChronoUnit.SECONDS.between(createdTime, currentTime).toInt()
            val minDiff = ChronoUnit.MINUTES.between(createdTime, currentTime).toInt()
            val hourDiff = ChronoUnit.HOURS.between(createdTime, currentTime).toInt()
            val dayDiff = ChronoUnit.DAYS.between(createdTime, currentTime).toInt()
            val monthDiff = ChronoUnit.MONTHS.between(createdTime, currentTime).toInt()
            val yearDiff = ChronoUnit.YEARS.between(createdTime, currentTime).toInt()
            Log.d(TAG, "timeDiff - ${minDiff} ${hourDiff} ${dayDiff} ${monthDiff} ${yearDiff}")

            if (minDiff == 0) return "${secondDiff}초 전"
            else if (hourDiff == 0) return "${minDiff}분 전"
            else if (dayDiff == 0) return "${hourDiff}시간 전"
            else if (monthDiff == 0) return "${dayDiff}일 전"
            else if (yearDiff == 0) return "${monthDiff}달 전"
            else return "0초 전"
        }catch (e : DateTimeParseException){
            throw e
        }

    }


    private fun handleEvent(event: IssueViewModel.Event) =
        when (event) {
            is IssueViewModel.Event.UnknownException -> {
                Log.d(TAG, "ERROR ERROR ERROR - ${event.error}")
            }
            is IssueViewModel.Event.SuccessGetComments -> {
                commentAdapter.submitList(event.list)
            }
            is IssueViewModel.Event.SuccessPostComment -> {
                Log.d(TAG, "SuccessPostComment")

                val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.reviewEdittxt.windowToken, 0)

                binding.reviewEdittxt.setText("")

                viewModel.getComments(mainViewModel.issue.postIdx)
            }

            is IssueViewModel.Event.SuccessPostLike -> {

                binding.likeTxt.text = event.likeInfo.likescount.toString()
                binding.dislikeTxt.text = event.likeInfo.badscount.toString()

                if (event.likeInfo.likesBad) {

//                    binding.likeBtn.isChecked = !binding.likeBtn.isChecked
                    Log.d(TAG,"likesBad : ${event.likeInfo.likesBad}")
//
                    if (binding.dislikeBtn.isChecked){
                        binding.dislikeBtn.isChecked = false
                    }

                    binding.likeBtn.isEnabled = true
                }
                else{
//                    binding.dislikeBtn.isChecked = !binding.dislikeBtn.isChecked
                    Log.d(TAG,"likesBad : ${event.likeInfo.likesBad}")
//
                    if (binding.likeBtn.isChecked){
                        binding.likeBtn.isChecked = false
                    }

                    binding.dislikeBtn.isEnabled = true
                }

                1

            }
        }

}