package com.ggd.zendee.feature.main

import androidx.lifecycle.viewModelScope
import com.ggd.zendee.base.BaseViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel: BaseViewModel() {

    var isPreviousExist = false
    var previousPosition : LatLng? = null
    var previousCameraUpdate : CameraUpdate? = null

}

// test 1