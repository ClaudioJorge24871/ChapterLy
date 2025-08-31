package com.example.chapterly.domain.repository

import com.example.chapterly.domain.model.BookEntry

/**
 * Repository to handle functions involving Books coming from the User Library
 */
interface UserLibraryRepository {

    // Get all books saved by the user
    suspend fun getUserBooks(): List<BookEntry>

    // Save a book on User Library
    suspend fun saveUserBook(userBook: BookEntry): Boolean

    // Delete a book from user Library
    suspend fun deleteUserBook(userBook: BookEntry): Boolean
}