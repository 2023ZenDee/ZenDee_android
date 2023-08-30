package com.ggd.module;

import com.ggd.network.api.LoginApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideLoginApiFactory implements Factory<LoginApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideLoginApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public LoginApi get() {
    return provideLoginApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideLoginApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideLoginApiFactory(retrofitProvider);
  }

  public static LoginApi provideLoginApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideLoginApi(retrofit));
  }
}
