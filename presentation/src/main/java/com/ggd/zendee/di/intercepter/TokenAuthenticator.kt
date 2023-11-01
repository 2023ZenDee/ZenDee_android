package com.ggd.zendee.di.intercepter

import android.util.Log
import com.ggd.network.api.AuthApi
import com.ggd.repository.AuthRepository
import com.ggd.zendee.di.utils.BASE_URL
import com.ggd.zendee.utils.HiltApplication
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.internal.ComponentEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import javax.inject.Inject

// todo 1. accessToken 유효기간 줄여주세요
// todo 2. getAccessToken() 을 할 때, refreshToken 을 전달해줘야 하는 거 아닌가?
// todo 3. 어째서 "AccessToken이 없습니다."가 뜨는겨???


class TokenAuthenticator: Authenticator {

    private val refreshRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
//        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val authService = refreshRetrofit.create(AuthApi::class.java)

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.i("Authenticator", response.toString())
        Log.i("Authenticator", "토큰 재발급 시도")
        var newAccessToken = ""
        var request:Request? = null

        GlobalScope.launch {
            try {
                Log.i("Authenticator", "orgToken: ${HiltApplication.prefs.accessToken}")
                newAccessToken = authService.getAccessToken(
                    HiltApplication.prefs.refreshToken
                ).data
                Log.i("Authenticator", "토큰 재발급 성공 : $newAccessToken")
                HiltApplication.prefs.deleteAccessToken()
                HiltApplication.prefs.accessToken = newAccessToken
                request = response.request.newBuilder()
                    .removeHeader("accessToken").apply {
                        addHeader("accessToken", newAccessToken)
                    }.build()
                Log.i("Authenticator", "accessToken in header ${request!!.headers["accessToken"]}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return request
    }
}