package com.ggd.zendee.feature.map

import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.res.ColorStateList
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.ggd.model.Issue.IssueModel
import com.ggd.model.Issue.PostIssueDto
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentMapBinding
import com.ggd.zendee.feature.main.MainViewModel
import com.ggd.zendee.utils.repeatOnStarted
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.CameraUpdate.REASON_GESTURE
import com.naver.maps.map.CameraUpdate.REASON_LOCATION
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.currentCoroutineContext
import java.time.LocalDate
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timer
import kotlin.math.log

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding,MapViewModel>(R.layout.fragment_map),OnMapReadyCallback {

    override val viewModel: MapViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    
    val TAG = "젠디"

    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    var cameraPosition = CameraPosition(
        LatLng(0.343, 0.234), // 대상 지점
        16.0, // 줌 레벨
        90.0, // 기울임 각도
        0.0 // 베어링 각도
    )

    override fun start() {

        repeatOnStarted {
            viewModel.eventFlow.collect{ event -> handleEvent(event) }
        }

        binding.mapView.foregroundTintList = ColorStateList.valueOf(Color.parseColor("#55000000"))
        viewModel.mapView = binding.mapView
        viewModel.mapView.getMapAsync(this)

        Log.d("젠디", "location - ${getLatLng()?.latitude} ${getLatLng()?.longitude}")

        viewModel.locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        binding.writeBtn.setOnClickListener {
            setDialog()
        }

//        viewModel.getIssuesByLocation(lat = 8.20F, lng = 8.20F)
//        viewModel.postIssue(PostIssueDto("최히건 왔따감", "하하ㅏ핳","ㅁㄴㅇ",5.20F,5.20F, "위험"))

    }

    fun setDialog(){

        val dialog = TagSelectorDialog(requireContext())
        dialog.setDialog()

        dialog.setRecyclerview(viewModel.tagDataSet)

        dialog.setOnClicklistener(object : TagSelectorDialog.OnDialogClickListener{

            override fun onClicked(position: Int) {
                Log.d("젠디", "onClicked: tag - ${viewModel.tagDataSet[position]} ")
                mainViewModel.selectedTag = viewModel.tagDataSet[position]
                findNavController().navigate(R.id.action_mapFragment_to_writeFragment)
            }

        })

        dialog.showDialog()
0
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.mapView.onCreate(null)
        } else {
            viewModel.mapView.onCreate(savedInstanceState)
        }
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
        viewModel.mapView.onStart()
        Log.d("젠디", "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        viewModel.mapView.onResume()
        Log.d(TAG, "onResume: ")
        mainViewModel.isFirst = true
    }

    override fun onPause() {
        super.onPause()

        viewModel.mapView.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.mapView.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: ")
    }

    override fun onStop() {
        super.onStop()
        viewModel.mapView.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.mapView.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
        Log.d(TAG, "onDestroyView: ")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        viewModel.mapView.onLowMemory()
        Log.d(TAG, "onLowMemory: ")
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {

        viewModel.naverMap = naverMap

        naverMap.locationSource = viewModel.locationSource

        naverMap.buildingHeight = 0.5F
        naverMap.cameraPosition = cameraPosition
        naverMap.lightness = -0.03F

        val uiSettings = naverMap.uiSettings
        uiSettings.isZoomControlEnabled = false
        uiSettings.isCompassEnabled = false
        uiSettings.isLocationButtonEnabled = false
        uiSettings.isScrollGesturesEnabled = false
        uiSettings.isTiltGesturesEnabled = false
        uiSettings.isStopGesturesEnabled = false
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.minZoom = 16.0

        naverMap.addOnCameraChangeListener { reason, animated ->

            //Log.d("젠디","reason : $reason, animated : $animated")
            if (reason == REASON_GESTURE || reason == REASON_LOCATION) {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }

        }

        naverMap.setOnMapClickListener { pointF, latLng ->

            Log.d("젠디", "pointF - ${pointF}, latLng - ${latLng}")
            Log.d("젠디", "location - ${getLatLng()?.latitude} ${getLatLng()?.longitude}")

        }

        naverMap.uiSettings

        startTimer()
    }

    private fun setMarker(list : MutableList<IssueModel>?) : Boolean{
        //원근감 표시
        viewModel.markerList.clear()

        if (list == null) {
            Log.d("젠디", "setMarker: list - null입니다")

             return false
        }

        Log.d("젠디", "setMarker: list - ${list}")

        for (i in list){

            val marker = Marker()
            marker.isIconPerspectiveEnabled = true
            marker.position = LatLng(i.latitude.toDouble(),i.longitude.toDouble())
            marker.map = viewModel.naverMap
            marker.height = 240
            marker.width = 201
//
//            when(i.tag){
//
//                IssueTag.HOT -> marker.icon = OverlayImage.fromResource(R.drawable.hot_marker)
//                IssueTag.ALERT -> marker.icon = OverlayImage.fromResource(R.drawable.alert_marker)
//                IssueTag.HAPPY -> marker.icon = OverlayImage.fromResource(R.drawable.happy_marker)
//                IssueTag.NOTICE -> marker.icon = OverlayImage.fromResource(R.drawable.notice_marker)
//                IssueTag.ACTIVE -> marker.icon = OverlayImage.fromResource(R.drawable.active_marker)
//                IssueTag.LOVE -> marker.icon = OverlayImage.fromResource(R.drawable.love_marker)
//                IssueTag.LUCKY -> marker.icon = OverlayImage.fromResource(R.drawable.lucky_marker)
//            }

            marker.icon = OverlayImage.fromResource(R.drawable.lucky_marker)

            marker.setOnClickListener {

                marker.height = 400
                marker.width = 335

                val cameraUpdate = CameraUpdate.scrollAndZoomTo(
                    LatLng(i.latitude.toDouble(),i.longitude.toDouble()), 18.0
                )
                    .animate(CameraAnimation.Linear,300)
                    .finishCallback {

                        binding.writeBtn.visibility = View.GONE

                        val dialog = IssueDialog(requireContext())
                        dialog.showDialog(
                            title = i.title,
                            address = "",
                            time = i.updated_at,
                            views = i.views,
                            comment = 1
                        )

                        dialog.setOnClicklistener(object : IssueDialog.OnDialogClickListener{

                            override fun onClicked() {

                                mainViewModel.previousPosition = LatLng(i.latitude.toDouble(),i.longitude.toDouble())

                                mainViewModel.issueId = i.postIdx
                                mainViewModel.issue = i
                                findNavController().navigate(R.id.action_mapFragment_to_issueFragment)
                            }

                            override fun onDismissed() {
                                Log.d("최희건", "$marker")

                                binding.writeBtn.visibility = View.VISIBLE

                                val cameraZoomUpdate = CameraUpdate.scrollAndZoomTo(
                                    LatLng(viewModel.locationSource.lastLocation!!.latitude,viewModel.locationSource.lastLocation!!.longitude),
                                    16.0 )
                                    .animate(CameraAnimation.Linear,300)

                                marker.height = 240
                                marker.width = 201

                                viewModel.naverMap.moveCamera(cameraZoomUpdate)
                            }
                        })

                    }

                viewModel.naverMap.moveCamera(cameraUpdate)

                return@setOnClickListener(true)
            }

            viewModel.markerList.add(marker)

        }

        return true

    }

    private fun getLatLng() : Location?{

        val locationManager : LocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var currentLocation : Location?

        try {
            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        }catch (e : SecurityException){
            currentLocation = null
        }

        if (currentLocation != null ){
            return currentLocation
        }
        else {
            return null
        }

    }

    var previousLocation = LatLng(0.0,0.0)

    fun startTimer(){

        timer(period = 8000, initialDelay = 0){

            val location = getLatLng()

            if(location != null){

                Log.d("젠디"," startTimer - location - ${location}")

                if (location.latitude != previousLocation.latitude && location.longitude != previousLocation.longitude){

                    Log.d("젠디"," startTimer - location is different")

                    previousLocation = LatLng(location)

                    viewModel.getIssuesByLocation(location.latitude.toFloat(), location.longitude.toFloat())

                }

                else if (mainViewModel.isFirst){

                    Log.d("젠디"," startTimer - it's first")

                    viewModel.getIssuesByLocation(location.latitude.toFloat(), location.longitude.toFloat())

                    mainViewModel.isFirst = false
                }

            }

        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000

    }
    private fun handleEvent(event: MapViewModel.Event) =
        when (event) {

            is MapViewModel.Event.SuccessGetIssuesByLocation -> {
                setMarker(event.issueModels?.toMutableList())
            }
            is MapViewModel.Event.UnknownException -> Log.d("젠디", "ERROR - ${event.error}")
            MapViewModel.Event.SuccessPostIssue -> Log.d("젠디", "SuccessPostIssue - ${event}")
        }

}