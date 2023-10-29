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
        kotlin.runCatching {
            GlobalScope.launch {
//                newAccessToken = authService.getAccessToken(
//                    HiltApplication.prefs.refreshToken
//                ).data.toString()
            }
//            newAccessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Impvc2V1bmd3YW42MTNAZ21haWwuY29tIiwiaWF0IjoxNjk4NTgyMzk5LCJleHAiOjE2OTg1ODI0MDR9.gQs6QM-czT1Ma8NIJa0ZU39mWOntOhW44VNliE4NI-4"
        }.onSuccess {
            Log.i("Authenticator", "토큰 재발급 성공 : $newAccessToken")
            HiltApplication.prefs.deleteToken()
            HiltApplication.prefs.accessToken = newAccessToken
            response.request.newBuilder()
                .removeHeader("X-AUTH-TOKEN").apply {
                    addHeader("X-AUTH-TOKEN", newAccessToken)
                }.build() // 토큰 재발급이 성공했다면, 기존 헤더를 지우고, 새로운 해더를 단다.
        }.onFailure { e ->
            e.printStackTrace()
        }
        return null
    }
}