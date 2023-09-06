package com.ggd.zendee.feature.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ggd.model.login.requests.LoginDto
import com.ggd.repository.LoginRepository
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.utils.HiltApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    private val _loginState = MutableLiveData<Event>()
    val loginState : LiveData<Event> = _loginState

    fun login(loginDto: LoginDto) = viewModelScope.launch {
        kotlin.runCatching {
            loginRepository.login(loginDto)
        }.onSuccess {
            HiltApplication.prefs.accessToken = it.accessToken
            HiltApplication.prefs.refreshToken = it.refreshToken
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