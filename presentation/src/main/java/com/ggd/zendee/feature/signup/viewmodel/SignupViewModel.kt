package com.ggd.zendee.feature.signup.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ggd.model.auth.RegisterRequestModel
import com.ggd.model.email.EmailCheckRequestModel
import com.ggd.model.email.EmailRequestModel
import com.ggd.repository.AuthRepository
import com.ggd.repository.EmailRepository
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.feature.signup.screen.state.SignupState
import com.ggd.zendee.utils.HiltApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val emailRepository: EmailRepository
): BaseViewModel() {

    private var _nick = MutableLiveData<String>()
    val nick: LiveData<String> = _nick
    private var _userId = MutableLiveData<String>()
    val userId: LiveData<String> = _userId
    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    private var _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

//    private var _mailToken = MutableLiveData<String>()
//    val mailToken: LiveData<String> = _mailToken

    private var _emailIsChecked = MutableLiveData<Boolean>()
    val emailIsChecked: LiveData<Boolean> = _emailIsChecked

    private val _signupState = MutableSharedFlow<SignupState>()
    val signupState: SharedFlow<SignupState> = _signupState

    fun setNick(nick: String) { _nick.value = nick }
    fun setUserId(userId: String) { _userId.value = userId }
    fun setPassword(password: String) { _password.value = password }
    fun setEmail(email: String) { _email.value = email }

    private fun MutableLiveData<Boolean>.setBoolean(boolean: Boolean) { this.value = boolean }

    fun register(registerRequestModel: RegisterRequestModel) = viewModelScope.launch {
        kotlin.runCatching {
            authRepository.register(registerRequestModel)
        }.onSuccess {
            when(it.status) {
                201 -> {
                    Log.d(TAG, "register: signupSuccess!!")
                    _signupState.emit(SignupState(isSuccess = true))
                    HiltApplication.prefs.autoLogin = true
                    HiltApplication.prefs.accessToken = it.accessToken
                    HiltApplication.prefs.refreshToken = it.refreshToken
                }
                400 -> {
                    Log.d(TAG, "register: same email!!")
                    _signupState.emit(SignupState(error = it.message))
                }
            }
        }.onFailure { e ->
            Log.d(TAG, "register: $e")
            _signupState.emit(SignupState(error = "$e"))
        }
    }

    fun getEmailCode(emailRequestModel: EmailRequestModel) = viewModelScope.launch {
        kotlin.runCatching {
            emailRepository.deliverEmail(emailRequestModel)
        }.onSuccess {
            Log.d(TAG, "deliverEmail: ${it.message}")
//            _mailToken.value = it.data
        }.onFailure { e ->
            Log.d(TAG, "deliverEmail: $e")
        }
    }

    fun checkEmail(mailToken: String, emailCheckRequestModel: EmailCheckRequestModel) = viewModelScope.launch {
        kotlin.runCatching {
            emailRepository.checkEmail(mailToken, emailCheckRequestModel)
        }.onSuccess {
            Log.d(TAG, "checkEmail Success!!: $it.success")
            _emailIsChecked.setBoolean(it.success)
        }.onFailure { e ->
            Log.d(TAG, "checkEmail Failed..: $e")
        }
    }

    companion object {
        const val TAG = "SignupViewModel"
    }

}