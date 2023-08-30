package com.ggd.module

import com.ggd.network.api.IssueService
import com.ggd.repository.CommentRepository
import com.ggd.repository.CommentRepositoryImpl
import com.ggd.repository.IssueRepository
import com.ggd.repository.IssueRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

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
}