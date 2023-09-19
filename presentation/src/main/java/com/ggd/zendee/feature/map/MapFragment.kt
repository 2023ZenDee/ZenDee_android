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
import kotlin.math.atan2

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



        (activity as MainActivity).handleBottomNavigation(true)

        repeatOnStarted {
            viewModel.eventFlow.collect{ event -> handleEvent(event) }
        }

        viewModel.timer = Timer()
        viewModel.timerTask = object : TimerTask() {

            override fun run() {

                Log.d(TAG, "access token - ${HiltApplication.prefs.accessToken}")

                val location = getLatLng()

                if(location != null){

                    Log.d("젠디"," startTimer - location - ${location}")

                    viewModel.getIssuesByLocation(lat = location.latitude.toFloat()  , lng = location.longitude.toFloat())
                }

            }

        }

        val loc = IntArray(2)

        binding.mapView.getLocationOnScreen(loc)


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
        super.onDestroyView()
        viewModel.timer.cancel()
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

        binding.mapView

        naverMap.uiSettings

        //startTimer()
        viewModel.timer.schedule(viewModel.timerTask,0,delayTime)
    }

    private fun setMarker(list : MutableList<IssueModel>?) : Boolean{
        //원근감 표시

        if (list == null) {
            Log.d("젠디", "setMarker: list - null입니다")

             return false
        }

        for (i in viewModel.markerList){
            i.map = null
        }

        viewModel.markerList.clear()

        Log.d("젠디", "setMarker: list - ${list}")

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
                "사랑" ->currentMarker.icon = OverlayImage.fromResource(R.drawable.love_marker)
                "행운" -> currentMarker.icon = OverlayImage.fromResource(R.drawable.lucky_marker)
            }

            currentMarker.setOnClickListener {

                viewModel.timer.cancel()

                currentMarker.height = 400
                currentMarker.width = 335

                val cameraUpdate = CameraUpdate.scrollAndZoomTo(
                    LatLng(i.latitude.toDouble(),i.longitude.toDouble()), 18.0
                )
                    .animate(CameraAnimation.Linear,300)
                    .finishCallback {

                        binding.writeBtn.visibility = View.GONE

                        val dialog = IssueDialog(requireContext())

                        var time = ""

                        try{ time = timeToString(i.created_at) }
                        catch (e : DateTimeParseException){
                            time = "시간을 불러오지 못했습니다"
                        }

                        dialog.showDialog(
                            title = i.title,
                            address = i.address,
                            time = time,
                            views = i.views,
                            comment = i.comments,

                        )

                        dialog.setOnClicklistener(object : IssueDialog.OnDialogClickListener{

                            override fun onClicked() {

                                mainViewModel.issue = i
                                findNavController().navigate(R.id.action_mapFragment_to_issueFragment)
                            }

                            override fun onDismissed() {
                                Log.d("최희건", "$viewModel.markerList.last()")

                                binding.writeBtn.visibility = View.VISIBLE

                                val cameraZoomUpdate = CameraUpdate.scrollAndZoomTo(
                                    LatLng(viewModel.locationSource.lastLocation!!.latitude,viewModel.locationSource.lastLocation!!.longitude),
                                    16.0 )
                                    .animate(CameraAnimation.Linear,300)

                                currentMarker.height = 240
                                currentMarker.width = 201

                                viewModel.naverMap.moveCamera(cameraZoomUpdate)

                                viewModel.timer =  Timer()
                                viewModel.timerTask = object : TimerTask() {

                                    override fun run() {

                                        Log.d(TAG, "access token - ${HiltApplication.prefs.accessToken}")

                                        val location = getLatLng()

                                        if(location != null){

                                            Log.d("젠디"," startTimer - location - ${location}")

                                            viewModel.getIssuesByLocation(lat = location.latitude.toFloat()  , lng = location.longitude.toFloat())
                                        }

                                    }

                                }

                                viewModel.timer.schedule(viewModel.timerTask,0,delayTime)
                            }
                        })

                    }

                viewModel.naverMap.moveCamera(cameraUpdate)

                return@setOnClickListener(true)
            }
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

    fun startTimer(){

        timer(period = 8000, initialDelay = 0){

            Log.d(TAG, "access token - ${HiltApplication.prefs.accessToken}")

            val location = getLatLng()

            if(location != null){

                Log.d("젠디"," startTimer - location - ${location}")

                viewModel.getIssuesByLocation(lat = location.latitude.toFloat()  , lng = location.longitude.toFloat())
            }

        }

    }


    fun timeToString(time : String) : String{

        try{
            val changedUpdatedAt = time.replace("Z", "")
            val createdTime =
                LocalDateTime.parse(changedUpdatedAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    .plusHours(9)

            val currentTime = LocalDateTime.now()
            val secondDiff = ChronoUnit.SECONDS.between(createdTime, currentTime).toInt()
            val minDiff = ChronoUnit.MINUTES.between(createdTime, currentTime).toInt()
            val hourDiff = ChronoUnit.HOURS.between(createdTime, currentTime).toInt()
            val dayDiff = ChronoUnit.DAYS.between(createdTime, currentTime).toInt()
            val monthDiff = ChronoUnit.MONTHS.between(createdTime, currentTime).toInt()
            val yearDiff = ChronoUnit.YEARS.between(createdTime, currentTime).toInt()
            Log.d(TAG, "timeDiff - ${minDiff} ${hourDiff} ${dayDiff} ${monthDiff} ${yearDiff}")

            if (minDiff == 0) return "${secondDiff}초 전"
            else if (hourDiff == 0) return "${minDiff}분 전"
            else if (dayDiff == 0) return "${hourDiff}시간 전"
            else if (monthDiff == 0) return "${dayDiff}일 전"
            else if (yearDiff == 0) return "${monthDiff}달 전"
            else return "0초 전"
        }catch (e : DateTimeParseException){
            throw e
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