package com.ggd.module;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class NetworkModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<Interceptor> headerInterceptorProvider;

  public NetworkModule_ProvideOkHttpClientFactory(Provider<Interceptor> headerInterceptorProvider) {
    this.headerInterceptorProvider = headerInterceptorProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(headerInterceptorProvider.get());
  }

  public static NetworkModule_ProvideOkHttpClientFactory create(
      Provider<Interceptor> headerInterceptorProvider) {
    return new NetworkModule_ProvideOkHttpClientFactory(headerInterceptorProvider);
  }

  public static OkHttpClient provideOkHttpClient(Interceptor headerInterceptor) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOkHttpClient(headerInterceptor));
  }
}
