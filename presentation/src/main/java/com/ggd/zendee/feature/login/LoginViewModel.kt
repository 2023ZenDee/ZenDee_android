package com.ggd.zendee.feature.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ggd.model.login.Login
import com.ggd.model.login.LoginDto
import com.ggd.model.login.TokenDto
import com.ggd.repository.LoginRepository
import com.ggd.zendee.base.BaseViewModel
import com.ggd.utils.HiltApplication
import com.ggd.zendee.utils.MutableEventFlow
import com.ggd.zendee.utils.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): BaseViewModel() {

    private val _eventFLow = MutableEventFlow<Event>()
    val eventFlow = _eventFLow.asEventFlow()

    fun login(loginDto: LoginDto) = viewModelScope.launch(Dispatchers.IO) {
        kotlin.runCatching {
            loginRepository.login(loginDto)
        }.onSuccess {
            event(Event.SuccessLogin(it))
        }.onFailure {
            event(Event.UnkownException)
            Log.d("error1", "${it.message}")
        }
    }

    fun token(tokenDto: TokenDto) = viewModelScope.launch(Dispatchers.IO) {
        kotlin.runCatching {
            loginRepository.token(tokenDto)
        }.onSuccess {
            HiltApplication.prefs.accessToken = it.accessToken
            HiltApplication.prefs.refreshToken = it.refreshToken
//            HiltApplication.prefs.autoLogin = true
            event(Event.SuccessToken)
        }.onFailure {
            event(Event.UnkownException)
            Log.d("error", "${it.message}")
        }
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFLow.emit(event)
        }
    }

    sealed class Event {
        data class SuccessLogin(val code: Login): Event()
        object SuccessToken: Event()
        object UnkownException: Event()
    }
}