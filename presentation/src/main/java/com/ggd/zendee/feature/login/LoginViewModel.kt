package com.ggd.zendee.feature.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ggd.model.auth.LoginRequestModel
import com.ggd.repository.AuthRepository
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.utils.HiltApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: AuthRepository
) : BaseViewModel() {

    private val _loginState = MutableLiveData<Event>()
    val loginState : LiveData<Event> = _loginState

    fun login(loginDto: LoginRequestModel) = viewModelScope.launch {
        kotlin.runCatching {
            loginRepository.login(loginDto)
        }.onSuccess {
            HiltApplication.prefs.accessToken = it.accessToken
            HiltApplication.prefs.refreshToken = it.refreshToken
            HiltApplication.prefs.autoLogin = true
            _loginState.value = Event.LoginSuccess
            Log.d(TAG, "login: LoginSuccess!")
        }.onFailure { e ->
            _loginState.value = Event.LoginFailed
            Log.d(TAG, "login: LoginFailed.., Cause : $e")
        }
    }

    enum class Event {
        LoginSuccess,
        LoginFailed
    }
    
    companion object {
        const val TAG = "LoginViewModel"
    }
}