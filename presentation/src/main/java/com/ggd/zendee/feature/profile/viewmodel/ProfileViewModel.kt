package com.ggd.zendee.feature.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ggd.model.user.ContentData
import com.ggd.repository.UserRepository
import com.ggd.zendee.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private var _myLikeList = MutableLiveData<List<ContentData>>()
    val myLikeList: LiveData<List<ContentData>> = _myLikeList

    fun getMyLikeContent() = viewModelScope.launch {
        kotlin.runCatching {
            userRepository.getMyLikeContent()
        }.onSuccess {
            Log.d(TAG, "getMyLikeContent: $it")
            _myLikeList.value = it.data
        }.onFailure { e ->
            Log.d(TAG, "getMyLikeContent: $e")
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }

}