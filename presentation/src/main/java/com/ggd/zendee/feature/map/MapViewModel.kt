package com.ggd.zendee.feature.map

import androidx.lifecycle.viewModelScope
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.utils.MutableEventFlow
import com.ggd.zendee.utils.asEventFlow
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.coroutines.launch

class MapViewModel : BaseViewModel() {

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asEventFlow()

    lateinit var mapView: MapView
    lateinit var locationSource: FusedLocationSource
    lateinit var naverMap: NaverMap


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

    val cameraPosition = CameraPosition(
        LatLng(37.5666102, 126.9783881), // 대상 지점
        16.0, // 줌 레벨
        90.0, // 기울임 각도
        0.0 // 베어링 각도
    )



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

    sealed class Event {
        object UnknownException : Event()
    }
}