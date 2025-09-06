package com.example.chapterly.di

import com.example.chapterly.domain.repository.BookRepository
import com.example.chapterly.domain.repository.UserLibraryRepository
import com.example.chapterly.domain.use_case.DeleteUserBookUseCase
import com.example.chapterly.domain.use_case.GetUserBookByISBNUseCase
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
    fun provideGetUserBookByISBNUseCase(repo: UserLibraryRepository): GetUserBookByISBNUseCase =
        GetUserBookByISBNUseCase(repo)

    @Provides
    fun provideSaveUserBookUseCase(userLibraryRepo: UserLibraryRepository): SaveUserBookUseCase =
        SaveUserBookUseCase(userLibraryRepo)

    @Provides
    fun provideDeleteUserBookUseCase(repo: UserLibraryRepository): DeleteUserBookUseCase =
        DeleteUserBookUseCase(repo)
}
