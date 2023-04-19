package com.ggd.zendee.feature.map

import com.naver.maps.geometry.LatLng

data class MarkerData(

    val positon : LatLng,
    val tag : IssueTag,
    val title : String,
    val context : String

)
