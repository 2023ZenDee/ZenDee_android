package com.ggd.zendee.di.module

import androidx.core.widget.AutoScrollHelper
import com.ggd.network.api.CommentService
import com.ggd.network.api.IssueService
import com.ggd.network.api.AuthApi
import com.ggd.network.api.EmailApi
import com.ggd.network.api.OauthApi
import com.ggd.zendee.di.utils.BASE_URL
import com.ggd.network.api.LikeService
import com.ggd.network.api.UserApi
import com.ggd.repository.AuthRepository
import com.ggd.zendee.di.intercepter.TokenAuthenticator
import com.ggd.zendee.di.qualifier.HeaderInterceptor
import com.ggd.zendee.di.qualifier.LoggingInterceptor
import com.ggd.zendee.utils.HiltApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    /* LoginApi Type의 객체 생성 */

    @Provides
    @Singleton
    fun provideIssueApi( retrofit: Retrofit): IssueService =
        retrofit.create(IssueService::class.java)


    @Provides
    @Singleton
    fun provideCommentApi(retrofit: Retrofit): CommentService =
        retrofit.create(CommentService::class.java)

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideOauthApi(retrofit: Retrofit): OauthApi =
        retrofit.create(OauthApi::class.java)

    @Provides
    @Singleton
    fun provideEmailApi(retrofit: Retrofit): EmailApi =
        retrofit.create(EmailApi::class.java)

    @Provides
    @Singleton
    fun provideLikeApi(retrofit: Retrofit): LikeService =
        retrofit.create(LikeService::class.java)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi =
        retrofit.create(UserApi::class.java)


    /* Retrofit Object 생성 */

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    /* OkHttp로 세부적인 네트워크 구성요소를 설정 */

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @HeaderInterceptor headerInterceptor: Interceptor,
        @LoggingInterceptor LoggerInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(LoggerInterceptor)
            .addInterceptor(headerInterceptor)
            .authenticator(TokenAuthenticator())

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    @LoggingInterceptor
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    @HeaderInterceptor
    fun provideHeaderInterceptor() = Interceptor { chain ->
        with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("accessToken", HiltApplication.prefs.accessToken)
//                .addHeader("accessToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6eyJ1c2VySWR4IjoxLCJ1c2VySWQiOiIxMSIsIm5pY2siOiJ6ZW5lZSIsInBhc3N3b3JkIjoiZEtTY2FZMjlQQkxqYXdzb2RFZllNL2RQT1RmL0V5Ni85d1ZMcWhoaVBEV25CYnNZdUM0cXdEaExVU2ZibHdGdVkyQ2ZjU3ZKRGpVR3o3NnBkWm4wYnc9PSIsImltYWdlIjoiaHR0cHM6Ly96ZW5kZWUxLnMzLmFwLW5vcnRoZWFzdC0yLmFtYXpvbmF3cy5jb20vdW5kZWZpbmVkLzE2OTgyNDQzMzcwNjFfJUVDJThBJUE0JUVEJTgxJUFDJUVCJUE2JUIwJUVDJTgzJUI3JTIwMjAyMy0wOC0zMSUyMDIyMDcwOC5wbmciLCJlbWFpbCI6InRlc3QxMUB0ZXN0LmNvbSIsImVtYWlsX2NoZWNrIjpmYWxzZSwicHJvdmlkZXIiOiJMT0NBTCIsInJvbGUiOiJVU0VSIiwiY3JlYXRlZF9hdCI6IjIwMjMtMDktMTVUMDM6MTk6MTkuMzc2WiIsInVwZGF0ZWRfYXQiOiIyMDIzLTA5LTE1VDAzOjE5OjE5LjM3M1oifSwiaWF0IjoxNjk4NzU3MDYwLCJleHAiOjE2OTg3NTcxMjB9.ytDs_Jl_axRgKN926UQCfOPKkIu1wPNXwsdb1c4KlFA")
                .build()
            proceed(newRequest)
        }
    }
}