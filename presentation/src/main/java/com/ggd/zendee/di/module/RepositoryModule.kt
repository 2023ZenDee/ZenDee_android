package com.ggd.zendee.di.module

import com.ggd.repository.AuthRepository
import com.ggd.repository.AuthRepositoryImpl
import com.ggd.repository.CommentRepository
import com.ggd.repository.CommentRepositoryImpl
import com.ggd.repository.EmailRepository
import com.ggd.repository.EmailRepositoryImpl
import com.ggd.repository.IssueRepository
import com.ggd.repository.IssueRepositoryImpl
import com.ggd.repository.OauthRepository
import com.ggd.repository.OauthRepositoryImpl
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
        impl : AuthRepositoryImpl
    ): AuthRepository = impl

    @Provides
    @Singleton
    fun provideOauthRepository(
        impl : OauthRepositoryImpl
    ): OauthRepository = impl

    @Provides
    @Singleton
    fun provideEmailRepository(
        impl : EmailRepositoryImpl
    ): EmailRepository = impl

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