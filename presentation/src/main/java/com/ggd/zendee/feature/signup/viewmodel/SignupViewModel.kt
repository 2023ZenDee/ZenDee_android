package com.ggd.zendee.feature.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ggd.zendee.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class SignupViewModel : BaseViewModel() {

    private var _nick = MutableLiveData<String>()
    private var _userId = MutableLiveData<String>()
    private var _password = MutableLiveData<String>()
    private var _email = MutableLiveData<String>()

    val nick : LiveData<String> = _nick
    val userId : LiveData<String> = _userId
    val password : LiveData<String> = _password
    val email : LiveData<String> = _email

    fun setNick(nick : String) {
        _nick.value = nick
    }

    fun setUserId(id : String) {
        _userId.value = id
    }

    fun setPassword(pwd : String) {
        _password.value = pwd
    }

    fun setEmail(email : String) {
        _email.value = email
    }
}