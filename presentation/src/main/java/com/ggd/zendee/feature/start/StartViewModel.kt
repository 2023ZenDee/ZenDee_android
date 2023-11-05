package com.ggd.zendee.feature.start

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ggd.model.oauth.GoogleOauthRequestModel
import com.ggd.repository.OauthRepository
import com.ggd.zendee.base.BaseViewModel
import com.ggd.zendee.feature.start.state.OauthLoginState
import com.ggd.zendee.utils.HiltApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val oauthRepository: OauthRepository
) : BaseViewModel() {

    private val _oauthLoginState = MutableSharedFlow<OauthLoginState>()
    val oauthLoginState: SharedFlow<OauthLoginState> = _oauthLoginState

    fun googleOauthLogin(googleOauthRequestModel: GoogleOauthRequestModel) = viewModelScope.launch {
        kotlin.runCatching {
            oauthRepository.googleOauthLogin(googleOauthRequestModel)
        }.onSuccess {
            Log.d(TAG, "googleOauthLogin: success $it")
            _oauthLoginState.emit(OauthLoginState(isSuccess = true))
            HiltApplication.prefs.deleteToken()
            HiltApplication.prefs.autoLogin = true
            HiltApplication.prefs.accessToken = it.accessToken
            HiltApplication.prefs.refreshToken = it.refreshToken
        }.onFailure { e ->
            Log.d(TAG, "googleOauthLogin: failed $e")
            _oauthLoginState.emit(OauthLoginState(error = "$e"))
        }
    }

    companion object {
        const val TAG = "StartViewModel"
    }
}