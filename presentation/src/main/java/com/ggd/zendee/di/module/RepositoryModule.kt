package com.ggd.zendee.di.module

import com.ggd.repository.CommentRepository
import com.ggd.repository.CommentRepositoryImpl
import com.ggd.repository.IssueRepository
import com.ggd.repository.IssueRepositoryImpl
import com.ggd.repository.LoginRepository
import com.ggd.repository.LoginRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideIssueRepository(
        impl: IssueRepositoryImpl
    ): IssueRepository = impl

    @Provides
    @Singleton
    fun provideCommentRepository(
        impl: CommentRepositoryImpl
    ): CommentRepository = impl

    @Provides
    @Singleton
    fun provideLoginRepository(
        impl : LoginRepositoryImpl
    ): LoginRepository = impl
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