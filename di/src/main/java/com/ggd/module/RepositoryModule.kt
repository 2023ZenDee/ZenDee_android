package com.ggd.module

import com.ggd.network.api.LoginApi
import com.ggd.repository.LoginRepository
import com.ggd.repository.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(
        api: LoginApi
    ): LoginRepository {
        return LoginRepositoryImpl(api)
    }


}