package com.ggd.module

import com.ggd.network.api.CommentService
import com.ggd.network.api.IssueService
import com.ggd.qualifier.BasicRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @BasicRetrofit
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.80.163.15:8070")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideIssueApi(@BasicRetrofit retrofit: Retrofit): IssueService =
        retrofit.create(IssueService::class.java)


    @Provides
    @Singleton
    fun provideCommentApi(@BasicRetrofit retrofit: Retrofit): CommentService =
        retrofit.create(CommentService::class.java)
}