package com.ggd.zendee.feature.main

import androidx.lifecycle.viewModelScope
import com.ggd.model.Issue.IssueModel
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.feature.map.IssueTag
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

@HiltViewModel
class MainViewModel @Inject constructor(

): BaseViewModel() {

    var isPreviousExist = false
    var previousPosition : LatLng? = null
    var previousCameraUpdate : CameraUpdate? = null

    var issueId : String = ""
    var issue : IssueModel = IssueModel("","","","","",0.0F,0.0F,"",0,"",0)

    var selectedTag : IssueTag = IssueTag.ALERT

    var isFirst : Boolean = true

}