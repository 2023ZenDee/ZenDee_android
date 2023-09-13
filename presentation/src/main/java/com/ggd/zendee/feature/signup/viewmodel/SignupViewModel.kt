package com.ggd.zendee.feature.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ggd.utils.PreferenceManager
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.utils.HiltApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class SignupViewModel: BaseViewModel() {
    private var _nick = MutableLiveData<String>()
    val nick: LiveData<String> = _nick
    private var _userId = MutableLiveData<String>()
    val userId: LiveData<String> = _userId
    private var _password = MutableLiveData<String>()
    val password: LiveData<String> = _password
    private var _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    fun setNick(nick: String) { _nick.value = nick }
    fun setUserId(userId: String) { _userId.value = userId }
    fun setPassword(password: String) { _password.value = password }
    fun setEmail(email: String) { _email.value = email }

}