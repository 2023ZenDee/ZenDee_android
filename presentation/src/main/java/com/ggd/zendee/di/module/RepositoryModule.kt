package com.ggd.zendee.di.module

import com.ggd.network.api.IssueService
import com.ggd.network.api.LoginApi
import com.ggd.repository.CommentRepository
import com.ggd.repository.CommentRepositoryImpl
import com.ggd.repository.IssueRepository
import com.ggd.repository.IssueRepositoryImpl
import com.ggd.repository.LoginRepository
import com.ggd.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideIssueRepository(

        impl: IssueRepositoryImpl

    ): IssueRepository

    @Binds
    @Singleton
    abstract fun provideCommentRepository(

        impl: CommentRepositoryImpl

    ): CommentRepository

    @Binds
    @Singleton
    abstract fun provideLoginRepository(
        impl : LoginRepositoryImpl
    ): LoginRepository
}