package com.ggd.zendee.feature.setting

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ggd.repository.UserRepository
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.feature.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val userRepository: UserRepository
): BaseViewModel() {

    // todo : Test
    fun getMyLikeContent() = viewModelScope.launch {
        kotlin.runCatching {
            userRepository.getMyLikeContent()
        }.onSuccess {
            Log.d(TAG, "getMyLikeContent: success!! $it")
        }.onFailure { e ->
            Log.d(TAG, "getMyLikeContent: failed.. $e")
        }
    }

    companion object {
        private const val TAG = "SettingViewModel"
    }
}