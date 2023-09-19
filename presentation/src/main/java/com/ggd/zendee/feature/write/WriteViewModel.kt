package com.ggd.zendee.feature.write

import androidx.lifecycle.viewModelScope
import com.ggd.model.Issue.IssueModel
import com.ggd.model.Issue.PostIssueDto
import com.ggd.repository.IssueRepository
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.feature.map.IssueTag
import com.ggd.zendee.feature.map.MapViewModel
import com.ggd.zendee.utils.MutableEventFlow
import com.ggd.zendee.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(

    private val issueRepository: IssueRepository

) : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<WriteViewModel.Event>()
    val eventFlow = _eventFlow.asEventFlow()

    var minute = 60

    val tagDataSet = mutableListOf<IssueTag>(
        IssueTag.ALERT,
        IssueTag.HOT,
        IssueTag.HAPPY,
        IssueTag.LUCKY,
        IssueTag.NOTICE,
        IssueTag.ACTIVE,
        IssueTag.LOVE
    )

    fun postIssue( issue : PostIssueDto ) = viewModelScope.launch(Dispatchers.IO){

        kotlin.runCatching {
            issueRepository.postIssue(issue)
        }.onSuccess {
            event(Event.SuccessPostIssue)
        }.onFailure {
            event(Event.UnknownException(it))
        }

    }

    private fun event(event : WriteViewModel.Event){

        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {

        object SuccessPostIssue : Event()
        data class UnknownException(val error : Throwable) : Event()
    }

}