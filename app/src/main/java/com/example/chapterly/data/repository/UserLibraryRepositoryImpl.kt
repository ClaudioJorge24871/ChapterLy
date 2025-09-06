package com.example.chapterly.data.repository

import com.example.chapterly.data.local.dao.BookDao
import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.repository.UserLibraryRepository
import com.example.chapterly.presentation.mapper.toDomain
import com.example.chapterly.presentation.mapper.toEntity
import com.example.chapterly.resources.Result
import com.example.chapterly.resources.Error
import com.example.chapterly.resources.UnknownError
import com.example.chapterly.resources.BookNotFoundError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserLibraryRepositoryImpl @Inject constructor(
    private val bookDao: BookDao
): UserLibraryRepository {

    override fun getUserBooks(): Flow<Result<List<BookEntry>, Error>> = flow {
        try {
            emitAll(
                bookDao.getAllBooks()
                    .map { entities -> Result.Success(entities.map { it.toDomain() }) }
            )
        } catch (e: Exception) {
            emit(Result.Error(UnknownError(e.message ?: "Unknown")))
        }
    }

    override fun getBookByISBN(isbn: String): Result<BookEntry, Error> {
        return try{
            val userBook = bookDao.getBookByISBN(isbn).toDomain()
            Result.Success(userBook)
        }catch (e: Exception){
            Result.Error(UnknownError(e.message ?: "Unknown"))
        }
    }

    override suspend fun saveUserBook(userBook: BookEntry): Result<BookEntry, Error> {
        return try {
            bookDao.insertBook(userBook.toEntity())
            Result.Success(userBook)
        }catch (e: Exception) {
            Result.Error(UnknownError(e.message ?: "Unknown"))
        }
    }

    override suspend fun deleteUserBook(userBook: BookEntry): Result<Unit, Error> {
        return try {
            bookDao.deleteBook(userBook.toEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(UnknownError(e.message ?: "Unknown"))
        }
    }
}