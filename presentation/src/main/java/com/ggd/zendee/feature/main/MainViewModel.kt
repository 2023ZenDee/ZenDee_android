package com.ggd.zendee.feature.main

import android.location.Location
import com.ggd.model.Issue.IssueModel
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.feature.map.IssueTag
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): BaseViewModel() {

    var issue : IssueModel = IssueModel(0,"","","","",0.0F,0.0F,"",0,"",0,"","",0,0,0,"")

    var selectedTag : IssueTag = IssueTag.ALERT

    var currentLocation : Location? = null

    var timer = Timer()
    lateinit var timerTask: TimerTask

    var isCancelled : Boolean = true

    var isNavigate : Boolean = false

}

