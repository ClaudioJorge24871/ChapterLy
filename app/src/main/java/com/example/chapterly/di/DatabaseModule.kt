package com.example.chapterly.di

import android.content.Context
import androidx.room.Room
import com.example.chapterly.data.local.BookDatabase
import com.example.chapterly.data.local.dao.BookDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BookDatabase{
        return Room.databaseBuilder(
            context,
            BookDatabase::class.java,
            "book_database"
        ).build()
    }

    @Provides
    fun provideBookDao(db: BookDatabase): BookDao = db.bookDao()
}