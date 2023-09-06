package com.ggd.zendee.feature.map

import androidx.lifecycle.viewModelScope
import com.ggd.model.Issue.IssueModel
import com.ggd.model.Issue.PostIssueDto
import com.ggd.repository.IssueRepository
import com.ggd.repository.IssueRepositoryImpl
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
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(

    private val issueRepository: IssueRepository

    ): BaseViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    lateinit var mapView: MapView
    lateinit var locationSource: FusedLocationSource
    lateinit var naverMap: NaverMap

    var IssueList = mutableListOf<IssueModel>()

    var markerList = mutableListOf<Marker>()
    var markerInfoList = mutableListOf<MarkerData>().apply {

        add(
            MarkerData(
                LatLng(35.6671544479555, 128.41228388669293),
                IssueTag.ACTIVE,
                "제목1제목1제목1제목1제목1",
                "내용내용내용내용111111111"
            )
        )
        add(
            MarkerData(
                LatLng(35.65921381734496, 128.41452606589644),
                IssueTag.ALERT,
                "제목2제목2제목2제목2제목2",
                "내용내용내용내용222"
            )
        )
        add(
            MarkerData(
                LatLng(35.66182930293654, 128.41207875255645),
                IssueTag.HOT,
                "제목3제목3제목3제목3제목3",
                "내용내용내3333"
            )
        )
        add(
            MarkerData(
                LatLng(35.66571029401473, 128.42015694863517),
                IssueTag.LOVE,
                "제목4444제목4",
                "내용44용내용4"
            )
        )
        add(
            MarkerData(
                LatLng(35.65911940329519, 128.42505583496046),
                IssueTag.NOTICE,
                "제목5제목5제5",
                "내용내용내용내55"
            )
        )
        add(
            MarkerData(
                LatLng(35.65738970652595, 128.40768258127332),
                IssueTag.HAPPY,
                "제목6제목666",
                "내용내용66666666"
            )
        )
        add(
            MarkerData(
                LatLng(35.66088517739557, 128.4068763134518),
                IssueTag.HOT,
                "제목777제목77",
                "내용내용내77777"
            )
        )
        add(
            MarkerData(
                LatLng(35.81885807290625, 128.63370166631233),
                IssueTag.HOT,
                "제목777제목77",
                "내용내용내77777"
            )
        )
        add(
            MarkerData(
                LatLng(35.821989844735086, 128.63261505359213),
                IssueTag.HOT,
                "제목777제목77",
                "내용내용내77777"
            )
        )
        add(
            MarkerData(
                LatLng(35.82950470171299, 128.62683150336323),
                IssueTag.HOT,
                "제목777제목77",
                "내용내용내77777"
            )
        )
        add(
            MarkerData(
                LatLng(35.820627938849995, 128.6287966934247),
                IssueTag.HOT,
                "제목777제목77",
                "내용내용내77777"
            )
        )
        add(
            MarkerData(
                LatLng(35.813786368552115, 128.6424926083676),
                IssueTag.HOT,
                "제목777제목77",
                "내용내용내77777"
            )
        )

    }


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

//        kotlin.runCatching {
//            issueRepository.getIssuesByLocation(lat, lng)
//        }.onSuccess {
//            event(Event.SuccessGetIssuesByLocation(it))
//        }.onFailure {
//            event(Event.UnknownException(it))
//        }

        var markerInfoList = mutableListOf<IssueModel>().apply {
            add(IssueModel("1","여기 아이유 떳다","허허ㅓ허허허","2023-05-12","2023-05-12",35.661438F,128.415958F,"0",0,"",0))
            add(IssueModel("2","여기 떳...냐?","아이유가 온다고???","2023-05-12","2023-05-12",35.664385F,128.417770F,"0",0,"",0))
            add(IssueModel("3","아몰랑","말도 안된다 진짜","2023-05-12","2023-05-12",35.662674F,128.413362F,"0",0,"",0))
            add(IssueModel("4","알아서 하시오","헤헤헤ㅔㅎ","2023-05-12","2023-05-12",35.663853F,128.412446F,"0",0,"",0))
        }

        event(Event.SuccessGetIssuesByLocation(
            markerInfoList
        ))

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