package com.example.chapterly.di

import com.example.chapterly.domain.repository.BookRepository
import com.example.chapterly.domain.repository.UserLibraryRepository
import com.example.chapterly.domain.use_case.DeleteUserBookUseCase
import com.example.chapterly.domain.use_case.GetUserBooksUseCase
import com.example.chapterly.domain.use_case.SaveUserBookUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetUserBooksUseCase(repo: UserLibraryRepository): GetUserBooksUseCase =
        GetUserBooksUseCase(repo)

    @Provides
    fun provideSaveUserBookUseCase(UserLibraryRepo: UserLibraryRepository): SaveUserBookUseCase =
        SaveUserBookUseCase(UserLibraryRepo)

    @Provides
    fun provideDeleteUserBookUseCase(repo: UserLibraryRepository): DeleteUserBookUseCase =
        DeleteUserBookUseCase(repo)
}
