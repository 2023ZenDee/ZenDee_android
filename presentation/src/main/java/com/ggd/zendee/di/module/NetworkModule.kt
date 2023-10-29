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
            .authenticator(tokenAuthenticator)

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
                .addHeader("AccessToken", HiltApplication.prefs.accessToken)
                .build()
            proceed(newRequest)
        }
    }
}

