package com.ggd.zendee.di.module

import com.ggd.network.api.IssueService
import com.ggd.network.api.LoginApi
import com.ggd.repository.CommentRepository
import com.ggd.repository.CommentRepositoryImpl
import com.ggd.repository.IssueRepository
import com.ggd.repository.IssueRepositoryImpl
import com.ggd.repository.LikeRepository
import com.ggd.repository.LikeRepositoryImpl
import com.ggd.repository.LoginRepository
import com.ggd.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.internal.immutableListOf
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

    @Binds
    @Singleton
    abstract fun provideLikeRepository(
        impl : LikeRepositoryImpl
    ): LikeRepository
}

//@Module
//@InstallIn(SingletonComponent::class)
//class RepositoryModule {
//
//    @Binds
//    @Singleton
//    fun provideIssueRepository(impl: IssueRepositoryImpl): IssueRepository = impl
//
//
//    @Binds
//    @Singleton
//    fun provideCommentRepository(impl: CommentRepositoryImpl) : CommentRepository = impl
//
//    @Binds
//    @Singleton
//    fun provideLoginRepository(impl : LoginRepositoryImpl): LoginRepository = impl
//}