package com.ggd.zendee.feature.map

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.UiThread
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
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
import java.time.LocalDate
import kotlin.math.log

class MapFragment : BaseFragment<FragmentMapBinding,MapViewModel>(R.layout.fragment_map),OnMapReadyCallback {

    override val viewModel: MapViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    override fun start() {

//        repeatOnStarted {
//            viewModel.eventFlow.collect{ event -> handleEvent(event) }
//        }

        binding.mapView.foregroundTintList = ColorStateList.valueOf(Color.parseColor("#55000000"))
        viewModel.mapView = binding.mapView
        viewModel.mapView.getMapAsync(this)

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
    }

    override fun onResume() {
        super.onResume()
        viewModel.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()

        viewModel.mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        viewModel.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.mapView.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback.remove()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        viewModel.mapView.onLowMemory()
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {

        viewModel.naverMap = naverMap

        naverMap.locationSource = viewModel.locationSource

        naverMap.buildingHeight = 0.5F
        naverMap.cameraPosition = viewModel.cameraPosition
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

            Log.d("젠디","reason : $reason, animated : $animated")
            if (reason == REASON_GESTURE || reason == REASON_LOCATION) {
                naverMap.locationTrackingMode = LocationTrackingMode.Follow
            }

        }

        naverMap.setOnMapClickListener { pointF, latLng ->

            Log.d("젠디", "pointF - ${pointF}, latLng - ${latLng}")
        }

        setMark(viewModel.markerInfoList)

        naverMap.uiSettings
    }

    private fun setMark(list : MutableList<MarkerData>) {
        //원근감 표시

        for (i in list){

            val marker = Marker()
            marker.isIconPerspectiveEnabled = true
            marker.position = i.positon
            marker.map = viewModel.naverMap
            marker.height = 240
            marker.width = 201

            when(i.tag){

                IssueTag.HOT -> marker.icon = OverlayImage.fromResource(R.drawable.hot_marker)
                IssueTag.ALERT -> marker.icon = OverlayImage.fromResource(R.drawable.alert_marker)
                IssueTag.HAPPY -> marker.icon = OverlayImage.fromResource(R.drawable.happy_marker)
                IssueTag.NOTICE -> marker.icon = OverlayImage.fromResource(R.drawable.notice_marker)
                IssueTag.ACTIVE -> marker.icon = OverlayImage.fromResource(R.drawable.active_marker)
                IssueTag.LOVE -> marker.icon = OverlayImage.fromResource(R.drawable.love_marker)
                IssueTag.LUCKY -> marker.icon = OverlayImage.fromResource(R.drawable.lucky_marker)
            }

            marker.setOnClickListener {

                marker.height = 400
                marker.width = 335

                val cameraUpdate = CameraUpdate.scrollAndZoomTo( i.positon, 18.0 )
                    .animate(CameraAnimation.Linear,300)
                    .finishCallback {

                        binding.writeBtn.visibility = View.GONE

                        val dialog = IssueDialog(requireContext())
                        dialog.showDialog()

                        dialog.setOnClicklistener(object : IssueDialog.OnDialogClickListener{

                            override fun onClicked() {

                                mainViewModel.previousPosition = i.positon
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