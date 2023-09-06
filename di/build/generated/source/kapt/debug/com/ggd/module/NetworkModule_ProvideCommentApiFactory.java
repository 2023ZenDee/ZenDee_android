package com.ggd.module;

import com.ggd.network.api.CommentService;
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
public final class NetworkModule_ProvideCommentApiFactory implements Factory<CommentService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideCommentApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public CommentService get() {
    return provideCommentApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideCommentApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideCommentApiFactory(retrofitProvider);
  }

  public static CommentService provideCommentApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideCommentApi(retrofit));
  }
}
