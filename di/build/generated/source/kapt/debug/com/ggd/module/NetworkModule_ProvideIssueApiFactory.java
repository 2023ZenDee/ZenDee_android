package com.ggd.module;

import com.ggd.network.api.IssueService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("com.ggd.qualifier.BasicRetrofit")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class NetworkModule_ProvideIssueApiFactory implements Factory<IssueService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideIssueApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public IssueService get() {
    return provideIssueApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideIssueApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideIssueApiFactory(retrofitProvider);
  }

  public static IssueService provideIssueApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideIssueApi(retrofit));
  }
}
