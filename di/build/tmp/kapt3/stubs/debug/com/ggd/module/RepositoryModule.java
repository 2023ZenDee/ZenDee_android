package com.ggd.module;

@dagger.Module
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007\u00a8\u0006\u000e"}, d2 = {"Lcom/ggd/module/RepositoryModule;", "", "()V", "provideCommentRepository", "Lcom/ggd/repository/CommentRepository;", "impl", "Lcom/ggd/repository/CommentRepositoryImpl;", "provideIssueRepository", "Lcom/ggd/repository/IssueRepository;", "Lcom/ggd/repository/IssueRepositoryImpl;", "provideLoginRepository", "Lcom/ggd/repository/LoginRepository;", "api", "Lcom/ggd/network/api/LoginApi;", "di_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class RepositoryModule {
    @org.jetbrains.annotations.NotNull
    public static final com.ggd.module.RepositoryModule INSTANCE = null;
    
    private RepositoryModule() {
        super();
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.ggd.repository.IssueRepository provideIssueRepository(@org.jetbrains.annotations.NotNull
    com.ggd.repository.IssueRepositoryImpl impl) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.ggd.repository.CommentRepository provideCommentRepository(@org.jetbrains.annotations.NotNull
    com.ggd.repository.CommentRepositoryImpl impl) {
        return null;
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.ggd.repository.LoginRepository provideLoginRepository(@org.jetbrains.annotations.NotNull
    com.ggd.network.api.LoginApi api) {
        return null;
    }
}