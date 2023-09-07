package com.ggd.zendee.feature.signup

import com.ggd.zendee.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class SignupViewModel : BaseViewModel() {
    var nick: String = ""
    var userId: String = ""
    var password: String = ""
    var email: String = ""
}