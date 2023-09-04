package com.ggd.zendee.feature.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ggd.model.login.requests.LoginDto
import com.ggd.repository.LoginRepository
import com.ggd.zendee.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    fun login(loginDto: LoginDto) = viewModelScope.launch {
        kotlin.runCatching {
            loginRepository.login(loginDto)
        }.onSuccess { code ->
            Log.d(TAG, "login: LoginSuccess!, code : $code")
        }.onFailure { e ->
            Log.d(TAG, "login: LoginFailed.., Cause : $e")
        }
    }
    
    companion object {
        const val TAG = "LoginViewModel"
    }
}