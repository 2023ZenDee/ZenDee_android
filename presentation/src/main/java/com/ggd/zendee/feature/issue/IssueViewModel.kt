package com.ggd.zendee.feature.issue

import androidx.lifecycle.viewModelScope
import com.ggd.model.Issue.IssueModel
import com.ggd.model.Issue.PostIssueDto
import com.ggd.repository.IssueRepository
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

    private val issueRepository: IssueRepository

    ) : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<IssueViewModel.Event>()
    val eventFlow = _eventFlow.asEventFlow()




    sealed class Event {
        data class UnknownException(val error : Throwable) : IssueViewModel.Event()

    }

}