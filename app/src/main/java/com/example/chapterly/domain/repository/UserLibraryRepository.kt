package com.example.chapterly.domain.repository

import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.resources.Result
import com.example.chapterly.resources.Error
import kotlinx.coroutines.flow.Flow

/**
 * Repository to handle functions involving Books coming from the User Library
 */
interface UserLibraryRepository {

    // Get all books saved by the user
    fun getUserBooks(): Flow<Result<List<BookEntry>, Error>>

    // Get a book by its ISBN
    fun getBookByID(id: Int): Flow<Result<BookEntry, Error>>

    // Save a book on User Library
    suspend fun saveUserBook(userBook: BookEntry): Result<BookEntry, Error>

    suspend fun updateUserBook(userBook: BookEntry): Result<BookEntry, Error>

    // Delete a book from user Library
    suspend fun deleteUserBook(userBook: BookEntry): Result<Unit, Error>
}