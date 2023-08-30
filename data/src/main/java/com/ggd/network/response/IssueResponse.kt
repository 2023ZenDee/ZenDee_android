package com.ggd.network.response

import dagger.multibindings.IntoMap

data class IssueResponse(

    val postIdx : String,
    val title : String,
    val content: String,
    val created_at : String,
    val updated_at : String,
    val latitude : Float,
    val longitude : Float,
    val deleted_at : String,
    val authorIdx : Int,
    val postImg : String,
    val views : Int

)
