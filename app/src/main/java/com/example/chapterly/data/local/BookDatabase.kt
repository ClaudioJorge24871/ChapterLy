package com.example.chapterly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.chapterly.data.local.dao.BookDao
import com.example.chapterly.data.local.entities.BookEntryEntity

@Database(
    entities = [BookEntryEntity::class],
    version = 2,
    exportSchema = false
)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao() : BookDao
}