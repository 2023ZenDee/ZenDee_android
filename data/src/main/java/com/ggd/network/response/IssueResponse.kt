package com.ggd.network.response

import dagger.multibindings.IntoMap

data class IssueResponse(

    val postIdx : Int,
    val title : String,
    val content: String,
    val created_at : String,
    val updated_at : String,
    val latitude : Float,
    val longitude : Float,
    val deleted_at : String,
    val authorIdx : Int,
    val postImg : String?,
    val views : Int,
    val tags : String,
    val user : String,
    val likes : Int,
    val bads : Int,
    val comments : Int,
    val address : String?,
    val userLikesPost : Boolean?

)
