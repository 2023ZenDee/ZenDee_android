package com.ggd.zendee.feature.map

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PointF
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.activity.OnBackPressedCallback
import androidx.annotation.UiThread
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ggd.model.Issue.IssueModel
import com.ggd.zendee.R
import com.ggd.zendee.base.BaseFragment
import com.ggd.zendee.databinding.FragmentMapBinding
import com.ggd.zendee.feature.main.MainActivity
import com.ggd.zendee.feature.main.MainViewModel
import com.ggd.zendee.utils.HiltApplication
import com.ggd.zendee.utils.repeatOnStarted
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.CameraUpdate.REASON_GESTURE
import com.naver.maps.map.CameraUpdate.REASON_LOCATION
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding,MapViewModel>(R.layout.fragment_map),OnMapReadyCallback {

    override val viewModel: MapViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    
    val TAG = "젠디"

    val delayTime = 10000L

    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    var cameraPosition = CameraPosition(
        LatLng(0.343, 0.234), // 대상 지점
        16.0, // 줌 레벨
        180.0, // 기울임 각도
        0.0 // 베어링 각도
    )

    override fun start() {

        Log.d(TAG,"MapFragment - start() called")

        (activity as MainActivity).handleBottomNavigation(true)

        repeatOnStarted {
            viewModel.eventFlow.collect{ event -> handleEvent(event) }
        }

        val loc = IntArray(2)

        binding.mapView.getLocationOnScreen(loc)

        mainViewModel.isNavigate = false

        binding.mapView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {

            override fun onGlobalLayout() {

                binding.mapView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                Log.d(TAG, "mapview : ${(binding.mapView.height) / 2 } ${binding.mapView.width / 2}")
                viewModel.center_x = (binding.mapView.width / 2)
                viewModel.center_y = (binding.mapView.height / 2)
                viewModel.mapview_height = binding.mapView.height
            }
        })

        binding.mapView.foregroundTintList = ColorStateList.valueOf(Color.parseColor("#55000000"))
        viewModel.mapView = binding.mapView
        viewModel.mapView.getMapAsync(this)

        Log.d("젠디", "location - ${getLatLng()?.latitude} ${getLatLng()?.longitude}")

        viewModel.locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        binding.writeBtn.setOnClickListener {
            setDialog()
        }
    }

    fun setDialog(){

        val dialog = TagSelectorDialog(requireContext())
        dialog.setDialog()

        dialog.setRecyclerview(viewModel.tagDataSet)

        dialog.setOnClicklistener(object : TagSelectorDialog.OnDialogClickListener{

            override fun onClicked(position: Int) {
                Log.d("젠디", "onClicked: tag - ${viewModel.tagDataSet[position]} ")
                mainViewModel.selectedTag = viewModel.tagDataSet[position]
                mainViewModel.currentLocation = getLatLng()

                findNavController().navigate(R.id.action_mapFragment_to_writeFragment)
            }

        })

        dialog.showDialog()

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
        if(!mainViewModel.isCancelled){
            mainViewModel.timer.cancel()
            mainViewModel.timerTask.cancel()
            Log.d(TAG, "onDestroy timer cancel - ${mainViewModel.timer}")

            mainViewModel.isCancelled = !mainViewModel.isCancelled
        }
        super.onDestroyView()
        onBackPressedCallback.remove()
        Log.d(TAG, "onDestroyView: ")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        viewModel.mapView.onLowMemory()
        Log.d(TAG, "onLowMemory: ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach: ")

    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {

        viewModel.naverMap = naverMap

        naverMap.locationSource = viewModel.locationSource

        naverMap.buildingHeight = 0.5F
        naverMap.cameraPosition = cameraPosition
        naverMap.lightness = -0.03F

        val uiSettings = naverMap.uiSettings
        uiSettings.isZoomControlEnabled = true
        uiSettings.isCompassEnabled = false
        uiSettings.isLocationButtonEnabled = false
        uiSettings.isScrollGesturesEnabled = false
        uiSettings.isTiltGesturesEnabled = false
        uiSettings.isStopGesturesEnabled = false
        uiSettings.isScaleBarEnabled = false
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        naverMap.minZoom = 16.0

        naverMap.addOnCameraChangeListener { reason, animated ->

            if (reason == REASON_GESTURE || reason == REASON_LOCATION) {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }

        }

        naverMap.setOnMapClickListener { pointF, latLng ->

            Log.d("젠디", "pointF - ${pointF}, latLng - ${latLng}")

            Log.d(TAG, "각도 계산 : ${atan2(viewModel.center_y - pointF.y ,viewModel.center_x - pointF.x) * 180 / Math.PI}")

            Log.d("젠디", "location - ${getLatLng()?.latitude} ${getLatLng()?.longitude}")

        }

        binding.mapView.setOnTouchListener { view, motionEvent ->

            val dX = motionEvent.x - viewModel.center_x.toDouble()
            val dY = -(motionEvent.y - viewModel.center_y.toDouble())

            var degree = atan2(dY, dX) * (180 / Math.PI) + 180

            var cameraPosition = naverMap.cameraPosition
            viewModel.cameraRotation = cameraPosition.bearing

            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    viewModel.prevDegree = degree

                }
                MotionEvent.ACTION_MOVE -> {
                    viewModel.cameraRotation = (viewModel.cameraRotation + (degree - viewModel.prevDegree))
                    viewModel.prevDegree = degree
                }
            }

            val cameraUpdate = CameraUpdate.toCameraPosition(CameraPosition(cameraPosition.target, cameraPosition.zoom, cameraPosition.tilt, viewModel.cameraRotation)).animate(CameraAnimation.None)
            naverMap.moveCamera(cameraUpdate)

            naverMap.locationTrackingMode = LocationTrackingMode.Follow

            return@setOnTouchListener false
        }

        binding.root.setOnTouchListener { view, motionEvent ->
            return@setOnTouchListener viewModel.isntTouchable
        }

        naverMap.uiSettings

        if (mainViewModel.isCancelled){
            mainViewModel.timer = Timer()
            mainViewModel.timerTask = object : TimerTask() {

                override fun run() {

                    Log.d(TAG, "access token - ${HiltApplication.prefs.accessToken}")

                    val location = getLatLng()

                    if (location != null) {

                        Log.d("젠디", " startTimer - location - ${location}")

                        viewModel.getIssuesByLocation(
                            lat = location.latitude.toFloat(),
                            lng = location.longitude.toFloat()
                        )
                    }

                }

            }

            mainViewModel.timer.schedule(mainViewModel.timerTask, 0, delayTime)

            mainViewModel.isCancelled = !mainViewModel.isCancelled

        }
        Log.d(TAG, "onMapReady timer - ${mainViewModel.timer}")
    }

    private fun setMarker(list : MutableList<IssueModel>?) : Boolean{
        //원근감 표시
        if (list == null) {
            Log.d("젠디", "setMarker: list - null입니다")

             return false
        }

        val closeIndexList = mutableListOf<Int>()

        var clickedMarkerIndex = 0

        fun findInBoundary(item : IssueModel) : List<IssueModel>{

            val returnList = mutableListOf<IssueModel>()

            var index = 0
            for (i in list){

                if (
                    sqrt((item.longitude - i.longitude).pow(2)
                            + (item.latitude - i.latitude).pow(2)) <= 0.0003
                ){
                    returnList.add(i)
                    closeIndexList.add(index)

                    if(item == i) {
                        clickedMarkerIndex = index
                    }

                }
                index++
            }

            Log.d(TAG, "findInBoundary: returnList - $returnList")
            Log.d(TAG, "findInBoundary: closeIndexList - $closeIndexList")

            return returnList
        }

        for (i in viewModel.markerList){
            i.map = null
        }

        viewModel.markerList.clear()

        Log.d("젠디", "setMarker: list - ${list}")

        var totalIndex = 0
        for (i in list){

            val marker = Marker()

            viewModel.markerList.add(marker)

            val currentMarker = viewModel.markerList.last()

            currentMarker.isIconPerspectiveEnabled = true
            currentMarker.position = LatLng(i.latitude.toDouble(),i.longitude.toDouble())
            currentMarker.map = viewModel.naverMap
            currentMarker.height = 240
            currentMarker.width = 201

            when(i.tags){

                "뜨거움" -> currentMarker.icon = OverlayImage.fromResource(R.drawable.hot_marker)
                "경고" -> currentMarker.icon = OverlayImage.fromResource(R.drawable.alert_marker)
                "재미" -> currentMarker.icon = OverlayImage.fromResource(R.drawable.happy_marker)
                "공지" -> currentMarker.icon = OverlayImage.fromResource(R.drawable.notice_marker)
                "활동" -> currentMarker.icon = OverlayImage.fromResource(R.drawable.active_marker)
                "사랑" -> currentMarker.icon = OverlayImage.fromResource(R.drawable.love_marker)
                "행운" -> currentMarker.icon = OverlayImage.fromResource(R.drawable.lucky_marker)
            }

            currentMarker.setOnClickListener {

                if(!mainViewModel.isCancelled){
                    mainViewModel.timer.cancel()
                    mainViewModel.timerTask.cancel()
                    Log.d(TAG, "MarkerClick timer cancel - ${mainViewModel.timer}")

                    mainViewModel.isCancelled = !mainViewModel.isCancelled
                }

                viewModel.isntTouchable = true

                binding.root.isClickable = false

                viewModel.previousCameraPosition = viewModel.naverMap.cameraPosition

                currentMarker.height = 400
                currentMarker.width = 335
                currentMarker.zIndex = 0

                val closeList = findInBoundary(i)

                var previousIndex = clickedMarkerIndex
                Log.d(TAG, "setMarker - previousIndex : $previousIndex  ")

                val cameraUpdate = CameraUpdate.scrollAndZoomTo(
                    LatLng(i.latitude.toDouble(),i.longitude.toDouble()), 18.0
                )
                    .animate(CameraAnimation.Linear,300)
                    .finishCallback {

                        viewModel.isntTouchable = false

                        binding.writeBtn.visibility = View.GONE

                        val dialog = IssueDialog(requireContext())

                        dialog.showDialog(closeList, clickedMarkerIndex)

                        dialog.setOnClicklistener(object : IssueDialog.OnDialogClickListener{

                            override fun onClicked(issue: IssueModel) {

                                mainViewModel.isNavigate = true

                                Log.d(TAG, "onClicked: ${mainViewModel.isNavigate}")

                                if(!mainViewModel.isCancelled){
                                    mainViewModel.timer.cancel()
                                    mainViewModel.timerTask.cancel()

                                    mainViewModel.isCancelled = !mainViewModel.isCancelled

                                }

                                mainViewModel.issue = issue

                                findNavController().navigate(R.id.action_mapFragment_to_issueFragment)
                            }

                            override fun onDismissed() {

                                if(mainViewModel.isCancelled && !mainViewModel.isNavigate){

                                    Log.d(TAG, "onDismissed: ${mainViewModel.isNavigate}")
                                    
                                    mainViewModel.timer = Timer()
                                    mainViewModel.timerTask = object : TimerTask() {

                                        override fun run() {

                                            val location = getLatLng()

                                            if (location != null) {

                                                Log.d("젠디", " startTimer - location - ${location}")

                                                viewModel.getIssuesByLocation(
                                                    lat = location.latitude.toFloat(),
                                                    lng = location.longitude.toFloat()
                                                )
                                            }
                                        }
                                    }

                                    mainViewModel.timer.schedule(
                                        mainViewModel.timerTask,
                                        0,
                                        delayTime
                                    )

                                    Log.d(TAG, "setMarker timer - ${mainViewModel.timer}")
                                    Log.d("최희건", "$viewModel.markerList.last()")
                                    
                                    mainViewModel.isCancelled = !mainViewModel.isCancelled

                                }

                                binding.writeBtn.visibility = View.VISIBLE

                                val cameraZoomUpdate = CameraUpdate.toCameraPosition(viewModel.previousCameraPosition).animate(CameraAnimation.Linear,300)
//                                val cameraZoomUpdate = CameraUpdate.scrollAndZoomTo(
//                                    LatLng(viewModel.locationSource.lastLocation!!.latitude,viewModel.locationSource.lastLocation!!.longitude), 16.0 )
//                                    .animate(CameraAnimation.Linear,300)

                                viewModel.markerList[previousIndex].height = 240
                                viewModel.markerList[previousIndex].width = 201
                                viewModel.markerList[previousIndex].zIndex = 0
                                viewModel.naverMap.moveCamera(cameraZoomUpdate)

                            }

                            override fun onChanged(index: Int, length : Int) {

                                Log.d(TAG, "onChanged - index : $index length : $length")

                                viewModel.markerList[previousIndex].height = 240
                                viewModel.markerList[previousIndex].width = 201
                                viewModel.markerList[previousIndex].zIndex = 0
                                Log.d(TAG, "onChanged previousIndex : ${previousIndex}")

                                if (index <= 0) dialog.setLeftArrow(false)
                                else dialog.setLeftArrow(true)

                                if(index >= length-1) dialog.setRightArrow(false)
                                else dialog.setRightArrow(true)

                                viewModel.markerList[closeIndexList[index]].height = 400
                                viewModel.markerList[closeIndexList[index]].width = 335
                                viewModel.markerList[closeIndexList[index]].zIndex = 1
                                Log.d(TAG, "onChanged closeIndex : ${index}")

                                val cameraUpdate = CameraUpdate.scrollAndZoomTo(
                                    LatLng(list[closeIndexList[index]].latitude.toDouble(),list[closeIndexList[index]].longitude.toDouble()), 18.0
                                ).animate(CameraAnimation.Linear,300)

                                viewModel.naverMap.moveCamera(cameraUpdate)

                                previousIndex = closeIndexList[index]

                            }

                        })

                    }

                viewModel.naverMap.moveCamera(cameraUpdate)

                return@setOnClickListener(true)
            }
            totalIndex++
        }

        return true
    }

    private fun getLatLng() : Location?{

        val locationManager : LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var currentLocation : Location?

        try {
            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        }catch (e : SecurityException){
            Log.d(TAG, "getLatLng: ${e}")
            currentLocation = null
        }

        if (currentLocation != null ){
            return currentLocation
        }
        else {
            return null
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000

    }
    private fun handleEvent(event: MapViewModel.Event) =
        when (event) {

            is MapViewModel.Event.SuccessGetIssuesByLocation -> {
                Log.d(TAG,"마커 추가추가")
                setMarker(event.issueModels?.toMutableList())
            }
            is MapViewModel.Event.UnknownException -> Log.d("젠디", "ERROR - ${event.error}")
            MapViewModel.Event.SuccessPostIssue -> Log.d("젠디", "SuccessPostIssue - ${event}")
        }

}