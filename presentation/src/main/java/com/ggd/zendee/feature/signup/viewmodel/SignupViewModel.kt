package com.ggd.zendee.feature.signup.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ggd.model.auth.RegisterRequestModel
import com.ggd.model.email.EmailCheckRequestModel
import com.ggd.model.email.EmailRequestModel
import com.ggd.network.request.email.EmailRequest
import com.ggd.repository.AuthRepository
import com.ggd.repository.EmailRepository
import com.ggd.utils.PreferenceManager
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.utils.HiltApplication
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private var _emailIsChecked = MutableLiveData<Boolean>()
    val emailIsChecked: LiveData<Boolean> = _emailIsChecked

    fun setNick(nick: String) { _nick.value = nick }
    fun setUserId(userId: String) { _userId.value = userId }
    fun setPassword(password: String) { _password.value = password }
    fun setEmail(email: String) { _email.value = email }

    fun setEmailIsChecked(isChecked: Boolean) {
        _emailIsChecked.value = isChecked
    }

    fun register(registerRequestModel: RegisterRequestModel) = viewModelScope.launch {
        kotlin.runCatching {
            authRepository.register(registerRequestModel)
        }.onSuccess {
            Log.d(TAG, "register: signupSuccess!!")
        }.onFailure { e ->
            Log.d(TAG, "register: $e")
        }
    }

    fun getEmailCode(emailRequestModel: EmailRequestModel) = viewModelScope.launch {
        kotlin.runCatching {
            emailRepository.deliverEmail(emailRequestModel)
        }.onSuccess {
            Log.d(TAG, "deliverEmail: ${it.message}")
        }.onFailure { e ->
            Log.d(TAG, "deliverEmail: $e")
        }
    }

    fun checkEmail(emailCheckRequestModel: EmailCheckRequestModel) = viewModelScope.launch {
        kotlin.runCatching {
            emailRepository.checkEmail(emailCheckRequestModel)
        }.onSuccess {
            Log.d(TAG, "checkEmail: $it.success")
            setEmailIsChecked(it.success)
        }.onFailure { e ->
            Log.d(TAG, "checkEmail: $e")
        }
    }

    companion object {
        const val TAG = "SignupViewModel"
    }

}