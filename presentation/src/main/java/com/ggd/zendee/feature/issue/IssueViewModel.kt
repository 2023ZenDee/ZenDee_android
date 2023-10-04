package com.ggd.zendee.feature.issue

import androidx.lifecycle.viewModelScope
import com.ggd.model.Issue.CommentModel
import com.ggd.model.Issue.IssueModel
import com.ggd.model.Issue.PostIssueDto
import com.ggd.model.LikeModel
import com.ggd.repository.CommentRepository
import com.ggd.repository.IssueRepository
import com.ggd.repository.LikeRepository
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.feature.map.MapViewModel
import com.ggd.zendee.utils.MutableEventFlow
import com.ggd.zendee.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(

    private val commentRepository: CommentRepository,
    private val likeRepository: LikeRepository

    ) : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<IssueViewModel.Event>()
    val eventFlow = _eventFlow.asEventFlow()

    private fun event(event : IssueViewModel.Event){

        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    fun getComments(id : Int) = viewModelScope.launch(Dispatchers.IO){

        kotlin.runCatching {
            commentRepository.getComments(id)
        }.onSuccess {
            event(IssueViewModel.Event.SuccessGetComments(it))
        }.onFailure {
            event(IssueViewModel.Event.UnknownException(it))
        }
    }

    fun postComment(id : Int, content : String) = viewModelScope.launch(Dispatchers.IO){

        kotlin.runCatching {
            commentRepository.postComment(id, content)
        }.onSuccess {
            event(IssueViewModel.Event.SuccessPostComment)
        }.onFailure {
            event(IssueViewModel.Event.UnknownException(it))
        }
    }

    fun postLike(id : Int, badLike : Boolean) = viewModelScope.launch(Dispatchers.IO){

        kotlin.runCatching {
            likeRepository.postLike(id,badLike)
        }.onSuccess {
            event(IssueViewModel.Event.SuccessPostLike(it))
        }.onFailure {
            event(IssueViewModel.Event.UnknownException(it))
        }
    }

    sealed class Event {

        data class SuccessPostLike(val likeInfo : LikeModel) : IssueViewModel.Event()
        object SuccessPostComment : IssueViewModel.Event()
        data class SuccessGetComments(val list : List<CommentModel>?) : IssueViewModel.Event()
        data class UnknownException(val error : Throwable) : IssueViewModel.Event()

    }

}