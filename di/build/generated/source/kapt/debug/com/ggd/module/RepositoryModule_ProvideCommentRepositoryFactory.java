package com.ggd.module;

import com.ggd.repository.CommentRepository;
import com.ggd.repository.CommentRepositoryImpl;
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
public final class RepositoryModule_ProvideCommentRepositoryFactory implements Factory<CommentRepository> {
  private final Provider<CommentRepositoryImpl> implProvider;

  public RepositoryModule_ProvideCommentRepositoryFactory(
      Provider<CommentRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public CommentRepository get() {
    return provideCommentRepository(implProvider.get());
  }

  public static RepositoryModule_ProvideCommentRepositoryFactory create(
      Provider<CommentRepositoryImpl> implProvider) {
    return new RepositoryModule_ProvideCommentRepositoryFactory(implProvider);
  }

  public static CommentRepository provideCommentRepository(CommentRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideCommentRepository(impl));
  }
}
