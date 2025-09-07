package com.example.chapterly.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chapterly.data.local.entities.BookEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book_entries")
    fun getAllBooks(): Flow<List<BookEntryEntity>>

    @Query("SELECT * FROM book_entries WHERE isbn == :isbn")
    fun getBookByISBN(isbn: String): BookEntryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntryEntity)

    @Update
    suspend fun updateBook(book: BookEntryEntity)

    @Delete
    suspend fun deleteBook(book: BookEntryEntity)
}