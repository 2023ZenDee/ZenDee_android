package com.ggd.zendee.feature.profile.viewmodel

import android.provider.ContactsContract.Profile
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.ggd.model.user.ContentData
import com.ggd.model.user.FixedInfo
import com.ggd.model.user.MyInfo
import com.ggd.model.user.MyInfoExceptForEmail
import com.ggd.repository.UserRepository
import com.ggd.zendee.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private var _myImage = MutableLiveData<String?>()
    val myImage: LiveData<String?> = _myImage
    private var _myNick = MutableLiveData<String>()
    val myNick: LiveData<String> = _myNick

    private var _myLikeList = MutableLiveData<List<ContentData>>()
    val myLikeList: LiveData<List<ContentData>> = _myLikeList

    private var _myUnLikeList = MutableLiveData<List<ContentData>>()
    val myUnLikeList: LiveData<List<ContentData>> = _myUnLikeList

    private var _myIssueList = MutableLiveData<List<ContentData>>()
    val myIssueList: LiveData<List<ContentData>> = _myIssueList

    private var _myCommentList = MutableLiveData<List<ContentData>>()
    val myCommentList: LiveData<List<ContentData>> = _myCommentList

    fun getMyInfo() = viewModelScope.launch {
        kotlin.runCatching {
            userRepository.getMyInfo()
        }.onSuccess {
            Log.d(TAG, "getMyInfo: success!! $it")
            _myImage.value = it.data.image
            _myNick.value = it.data.nick
        }.onFailure { e ->
            Log.d(TAG, "getMyInfo: failed.. $e")
        }
    }

    fun editMyImage(img: MultipartBody.Part?) = viewModelScope.launch {
        kotlin.runCatching {
            userRepository.editMyImage(img)
        }.onSuccess {
            Log.d(TAG, "editMyInfo: success!! $it")
            _myImage.value = it.data
        }.onFailure { e ->
            Log.d(TAG, "editMyInfo: failed.. $e")
        }
    }

    fun getMyLikeContent() = viewModelScope.launch {
        kotlin.runCatching {
            userRepository.getMyLikeContent()
        }.onSuccess {
            Log.d(TAG, "getMyLikeContent: success!! $it")
            _myLikeList.value = it.data
        }.onFailure { e ->
            Log.d(TAG, "getMyLikeContent: failed.. $e")
        }
    }

    fun getMyUnLikeContent() = viewModelScope.launch {
        kotlin.runCatching {
            userRepository.getMyUnLikeContent()
        }.onSuccess {
            Log.d(TAG, "getMyUnLikeContent: success!! $it")
            _myUnLikeList.value = it.data
        }.onFailure { e ->
            Log.d(TAG, "getMyUnLikeContent: failed.. $e")
        }
    }

    fun getMyIssueContent() = viewModelScope.launch {
        kotlin.runCatching {
            userRepository.getMyIssueContent()
        }.onSuccess {
            Log.d(TAG, "getMyIssueContent: success!! $it")
            _myIssueList.value = it.data
        }.onFailure { e ->
            Log.d(TAG, "getMyIssueContent: failed.. $e")
        }
    }

    fun getMyCommentContent() = viewModelScope.launch {
        kotlin.runCatching {
            userRepository.getMyCommentContent()
        }.onSuccess {
            Log.d(TAG, "getMyCommentContent: success!! $it")
            _myCommentList.value = it.data
        }.onFailure { e ->
            Log.d(TAG, "getMyCommentContent: failed.. $e")
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }

}