package com.ggd.module;

import com.ggd.repository.IssueRepository;
import com.ggd.repository.IssueRepositoryImpl;
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
public final class RepositoryModule_ProvideIssueRepositoryFactory implements Factory<IssueRepository> {
  private final Provider<IssueRepositoryImpl> implProvider;

  public RepositoryModule_ProvideIssueRepositoryFactory(
      Provider<IssueRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public IssueRepository get() {
    return provideIssueRepository(implProvider.get());
  }

  public static RepositoryModule_ProvideIssueRepositoryFactory create(
      Provider<IssueRepositoryImpl> implProvider) {
    return new RepositoryModule_ProvideIssueRepositoryFactory(implProvider);
  }

  public static IssueRepository provideIssueRepository(IssueRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideIssueRepository(impl));
  }
}
