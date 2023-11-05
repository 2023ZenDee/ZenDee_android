package com.ggd.model.user

import android.net.Uri
import okhttp3.MultipartBody
import java.io.File

data class FixedInfo(
    val img: MultipartBody.Part?,
    val nick: String,
)
