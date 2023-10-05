package com.ggd.zendee.feature.map

import androidx.lifecycle.viewModelScope
import com.ggd.model.Issue.IssueModel
import com.ggd.model.Issue.PostIssueDto
import com.ggd.repository.IssueRepository
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.utils.MutableEventFlow
import com.ggd.zendee.utils.asEventFlow
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(

    private val issueRepository: IssueRepository,

    ): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    lateinit var mapView: MapView
    lateinit var locationSource: FusedLocationSource
    lateinit var naverMap: NaverMap

    var previousCameraPosition =  CameraPosition(
        LatLng(0.343, 0.234), // 대상 지점
        16.0, // 줌 레벨
        180.0, // 기울임 각도
        0.0 // 베어링 각도
    )

    var isntTouchable = false

    var center_x = 0
    var center_y = 0

    var mapview_height = 0

    var prevDegree = 0.0

    var cameraRotation = 0.0

    var markerList = mutableListOf<Marker>()

    val tagDataSet = mutableListOf<IssueTag>(
        IssueTag.ALERT,
        IssueTag.HOT,
        IssueTag.HAPPY,
        IssueTag.LUCKY,
        IssueTag.NOTICE,
        IssueTag.ACTIVE,
        IssueTag.LOVE
        )


    private fun event(event : Event){

        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    fun getIssuesByLocation(lat : Float, lng : Float) = viewModelScope.launch(Dispatchers.IO){

        kotlin.runCatching {
            issueRepository.getIssuesByLocation(lat = lat, lng = lng)
        }.onSuccess {
            event(Event.SuccessGetIssuesByLocation(it))
        }.onFailure {
            event(Event.UnknownException(it))
        }

    }

    fun postIssue(issue : PostIssueDto) = viewModelScope.launch(Dispatchers.IO){

        kotlin.runCatching {
            issueRepository.postIssue(issue)
        }.onSuccess {
            event(Event.SuccessPostIssue)
        }.onFailure {
            event(Event.UnknownException(it))
        }
    }


    sealed class Event {

        data class SuccessGetIssuesByLocation(val issueModels : List<IssueModel>?) : Event()
        object SuccessPostIssue : Event()
        data class UnknownException(val error : Throwable) : Event()
    }
}