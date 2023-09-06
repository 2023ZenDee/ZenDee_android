package com.ggd.module;

import com.ggd.network.api.LoginApi;
import com.ggd.repository.LoginRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class RepositoryModule_ProvideLoginRepositoryFactory implements Factory<LoginRepository> {
  private final Provider<LoginApi> apiProvider;

  public RepositoryModule_ProvideLoginRepositoryFactory(Provider<LoginApi> apiProvider) {
    this.apiProvider = apiProvider;
  }

  @Override
  public LoginRepository get() {
    return provideLoginRepository(apiProvider.get());
  }

  public static RepositoryModule_ProvideLoginRepositoryFactory create(
      Provider<LoginApi> apiProvider) {
    return new RepositoryModule_ProvideLoginRepositoryFactory(apiProvider);
  }

  public static LoginRepository provideLoginRepository(LoginApi api) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideLoginRepository(api));
  }
}
