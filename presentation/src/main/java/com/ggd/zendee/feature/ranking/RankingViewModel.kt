package com.ggd.zendee.feature.ranking

import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ggd.model.Issue.IssueModel
import com.ggd.repository.IssueRepository
import com.ggd.repository.RankingRepositoryImpl
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.feature.login.LoginViewModel.Companion.TAG
import com.ggd.zendee.feature.map.MapViewModel
import com.ggd.zendee.utils.MutableEventFlow
import com.ggd.zendee.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val issueRepository: IssueRepository,
    private val rankingRepositoryImpl: RankingRepositoryImpl
): BaseViewModel() {


    var sortBy : String = "views"

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

    private val _eventFlow = MutableEventFlow<RankingViewModel.Event>()
    val eventFlow = _eventFlow.asEventFlow()

    var pagingData = rankingRepositoryImpl.getRank().cachedIn(viewModelScope)

//        .stateIn(
//            initialValue = IssueModel(0,"","","","",0.0F,0.0F,"",0,"",0,"","",0,0,0,"",null),
//            started = SharingStarted.WhileSubscribed(5000),
//            scope = viewModelScope
//        )

//    fun getPagingRank(sortBy : String, tagList : List<String>) : Flow<PagingData<IssueModel>>{
//        return rankingRepositoryImpl.getRank(sortBy,tagList)
//    }

    fun getPagingRank(sortBy : String, tagList : List<String>){
        Log.d(TAG,"RankingViewModel - getPagingRank() called")
        rankingRepositoryImpl.updateFilter(sortBy,tagList)
        pagingData = rankingRepositoryImpl.getRank().cachedIn(viewModelScope)

        event(Event.Success)
    }
    fun getRank(sortBy : String, tagList : List<String>) = viewModelScope.launch(Dispatchers.IO){

        kotlin.runCatching {
//            rankingRepositoryImpl.getRank(sortBy,tagList)
        }.onSuccess {
//            pagingData = it
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

        object Success : Event()
        data class SuccessGetRank(val issueModels : List<IssueModel>?) : Event()
        data class UnknownException(val error : Throwable) : Event()
    }

    fun getTagStringList() : List<String>{

        val list = mutableListOf<String>()

        for (i in tagList){
            if(i.isTagSelected) list.add(i.tagName)
        }

        return list
    }


}