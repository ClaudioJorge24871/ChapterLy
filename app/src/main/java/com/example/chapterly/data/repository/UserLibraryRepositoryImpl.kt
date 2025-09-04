package com.example.chapterly.data.repository

import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.repository.UserLibraryRepository
import com.example.chapterly.resources.Result
import com.example.chapterly.resources.Error
import com.example.chapterly.resources.UnknownError
import com.example.chapterly.resources.BookNotFoundError
import javax.inject.Inject

class UserLibraryRepositoryImpl @Inject constructor(

): UserLibraryRepository {

    private val userBooks = mutableListOf<BookEntry>()

    override suspend fun getUserBooks(): Result<List<BookEntry>,Error> {
        return Result.Success(userBooks.toList())
    }

    override suspend fun saveUserBook(userBook: BookEntry): Result<BookEntry, Error> {
        return try{
            userBooks.add(userBook)
            Result.Success(userBook)
        } catch (e: Exception) {
            Result.Error(UnknownError(e.message ?: "Unknown"))
        }
    }

    override suspend fun deleteUserBook(userBook: BookEntry): Result<Unit,Error> {
        val removed = userBooks.removeAll { it.book.isbn == userBook.book.isbn }
        return if (removed) Result.Success(Unit)
        else Result.Error(BookNotFoundError)
    }
}