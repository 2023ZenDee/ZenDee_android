package com.ggd.model.user

data class MyIssueContentResponseModel(
    val status: Int,
    val success: Boolean,
    val message : String,
    val data : List<ContentData>
)
