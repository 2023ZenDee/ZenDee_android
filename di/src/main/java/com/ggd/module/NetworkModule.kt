package com.ggd.module

import com.ggd.network.api.CommentService
import com.ggd.network.api.IssueService
import com.ggd.network.api.LoginApi
import com.ggd.qualifier.BasicRetrofit
import com.ggd.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideIssueApi(@BasicRetrofit retrofit: Retrofit): IssueService =
        retrofit.create(IssueService::class.java)


    @Provides
    @Singleton
    fun provideCommentApi(@BasicRetrofit retrofit: Retrofit): CommentService =
        retrofit.create(CommentService::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient(
        headerInterceptor: Interceptor,
        //LoggerInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)
        //okHttpClientBuilder.addInterceptor(LoggerInterceptor)
        okHttpClientBuilder.addInterceptor(headerInterceptor)

        return okHttpClientBuilder.build()
    }

    //@Provides
    //@Singleton
    //fun provideLoggingInterceptor(): HttpLoggingInterceptor =
    //    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    // Retrofit Client 인스턴스 생성
    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi =
        retrofit.create(LoginApi::class.java)

    @Provides
    @Singleton
    fun provideHeaderInterceptor() = Interceptor { chain ->
        with(chain) {
            val newRequest = request().newBuilder()
                //.addHeader("Authorization", HiltApplication.prefs.accessToken)
                .addHeader("Authorization", "")
                .build()
            proceed(newRequest)
       }
    }
}