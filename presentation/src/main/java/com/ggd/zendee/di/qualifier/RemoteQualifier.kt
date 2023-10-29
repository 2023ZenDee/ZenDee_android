package com.ggd.zendee.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BasicRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeaderInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoggingInterceptor