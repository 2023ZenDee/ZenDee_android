package com.ggd.zendee.feature.map

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.fragment.app.viewModels
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentMapBinding
import com.ggd.zendee.utils.repeatOnStarted
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource

class MapFragment : BaseFragment<FragmentMapBinding,MapViewModel>(R.layout.fragment_map),OnMapReadyCallback {

    override val viewModel: MapViewModel by viewModels()
    lateinit var mapView: MapView
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap


    var markerList = mutableListOf<Marker>()
    var markerInfoList = mutableListOf<MarkerData>().apply {

        add(MarkerData(LatLng(35.6671544479555,128.41228388669293),IssueTag.ACTIVE,"제목1제목1제목1제목1제목1","내용내용내용내용111111111"))
        add(MarkerData(LatLng(35.65921381734496,128.41452606589644),IssueTag.ALERT,"제목2제목2제목2제목2제목2","내용내용내용내용222"))
        add(MarkerData(LatLng(35.66182930293654,128.41207875255645),IssueTag.HOT,"제목3제목3제목3제목3제목3","내용내용내3333"))
        add(MarkerData(LatLng(35.66571029401473,128.42015694863517),IssueTag.LOVE,"제목4444제목4","내용44용내용4"))
        add(MarkerData(LatLng(35.65911940329519,128.42505583496046),IssueTag.NOTICE,"제목5제목5제5","내용내용내용내55"))
        add(MarkerData(LatLng(35.65738970652595,128.40768258127332),IssueTag.HAPPY,"제목6제목666","내용내용66666666"))
        add(MarkerData(LatLng(35.66088517739557,128.4068763134518),IssueTag.HOT,"제목777제목77","내용내용내77777"))

    }

    val cameraPosition = CameraPosition(
        LatLng(37.5666102, 126.9783881), // 대상 지점
        16.0, // 줌 레벨
        90.0, // 기울임 각도
        0.0 // 베어링 각도
    )

    override fun start() {

//        repeatOnStarted {
//            viewModel.eventFlow.collect{ event -> handleEvent(event) }
//        }
        binding.mapView.foregroundTintList = ColorStateList.valueOf(Color.parseColor("#55000000"))
        mapView = binding.mapView
        mapView.getMapAsync(this)

        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        return
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {


        this.naverMap = naverMap

        naverMap.locationSource = locationSource

        naverMap.buildingHeight = 0.5F
        naverMap.cameraPosition = cameraPosition

        val uiSettings = naverMap.uiSettings
        uiSettings.isZoomControlEnabled = false
        uiSettings.isCompassEnabled = false
        uiSettings.isLocationButtonEnabled = false
        uiSettings.isScrollGesturesEnabled = false
        uiSettings.isTiltGesturesEnabled = false
        uiSettings.isStopGesturesEnabled = false
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.minZoom = 16.0

        naverMap.addOnCameraChangeListener { i, b ->

            naverMap.locationTrackingMode = LocationTrackingMode.Follow

        }

        setMark(markerInfoList)

        naverMap.uiSettings
    }

    private fun setMark(list : MutableList<MarkerData>) {
        //원근감 표시

        for (i in list){

            val marker = Marker()
            marker.isIconPerspectiveEnabled = true
            marker.position = i.positon
            marker.map = naverMap

            when(i.tag){

                IssueTag.HOT -> marker.icon = OverlayImage.fromResource(R.drawable.hot_marker)
                IssueTag.ALERT -> marker.icon = OverlayImage.fromResource(R.drawable.alert_marker)
                IssueTag.HAPPY -> marker.icon = OverlayImage.fromResource(R.drawable.happy_marker)
                IssueTag.NOTICE -> marker.icon = OverlayImage.fromResource(R.drawable.notice_marker)
                IssueTag.ACTIVE -> marker.icon = OverlayImage.fromResource(R.drawable.active_marker)
                IssueTag.LOVE -> marker.icon = OverlayImage.fromResource(R.drawable.love_marker)
            }

            marker.setOnClickListener {

                Log.d("클릭됨", "${i.title}")

                return@setOnClickListener(true)

            }

            markerList.add(marker)

        }

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000

    }

    //    private fun handleEvent(event: MapViewModel.Event) =
//        when (event) {
//
//
//            MapViewModel.Event.UnknownException ->
//        }


}