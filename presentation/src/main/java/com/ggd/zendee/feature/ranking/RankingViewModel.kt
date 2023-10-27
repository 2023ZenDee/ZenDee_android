package com.ggd.zendee.feature.ranking

import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.viewModelScope
import com.ggd.model.Issue.IssueModel
import com.ggd.repository.IssueRepository
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.feature.map.MapViewModel
import com.ggd.zendee.utils.MutableEventFlow
import com.ggd.zendee.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val issueRepository: IssueRepository
): BaseViewModel() {


    private val _eventFlow = MutableEventFlow<RankingViewModel.Event>()
    val eventFlow = _eventFlow.asEventFlow()

    var rankList : List<IssueModel>? = null


    var tagList = mutableListOf<RankingTagData>(

        RankingTagData("경고", R.drawable.tag_checkbox_alert,true,0),
        RankingTagData("뜨거움", R.drawable.tag_checkbox_hot,true,1),
        RankingTagData("재미",R.drawable.tag_checkbox_happy,true,2),
        RankingTagData("행운", R.drawable.tag_checkbox_lucky,true,3),
        RankingTagData("공지", R.drawable.tag_checkbox_notice,true,4),
        RankingTagData("활동", R.drawable.tag_checkbox_active,true,5),
        RankingTagData("사랑",R.drawable.tag_checkbox_love,true,6),

        )

    var sortBy : String = "views"

    fun getRank(sortBy : String, tagList : List<String>) = viewModelScope.launch(Dispatchers.IO){

        kotlin.runCatching {
            issueRepository.getRank(sortBy, tagList)
        }.onSuccess {
            event(RankingViewModel.Event.SuccessGetRank(it))
        }.onFailure {
            event(RankingViewModel.Event.UnknownException(it))
        }

    }


    private fun event(event : RankingViewModel.Event){

        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {

        data class SuccessGetRank(val issueModels : List<IssueModel>?) : Event()
        data class UnknownException(val error : Throwable) : Event()
    }


}