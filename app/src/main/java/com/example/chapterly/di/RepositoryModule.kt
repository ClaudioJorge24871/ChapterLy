package com.example.chapterly.di

import com.example.chapterly.domain.repository.UserLibraryRepository
import com.example.chapterly.data.repository.UserLibraryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserLibraryRepository(
        impl: UserLibraryRepositoryImpl
    ): UserLibraryRepository
}
