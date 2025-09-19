package com.example.chapterly.data.repository

import com.example.chapterly.data.local.dao.BookDao
import com.example.chapterly.data.local.entities.BookEntryEntity
import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.model.Genre
import com.example.chapterly.domain.repository.UserLibraryRepository
import com.example.chapterly.presentation.mapper.toDomain
import com.example.chapterly.presentation.mapper.toEntity
import com.example.chapterly.resources.BookNotFoundError
import com.example.chapterly.resources.Result
import com.example.chapterly.resources.Error
import com.example.chapterly.resources.UnknownError
import com.example.chapterly.resources.UpdatingBookError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UserLibraryRepositoryImpl @Inject constructor(
    private val bookDao: BookDao,
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

    override fun getBookByID(id: Int): Flow<Result<BookEntry, Error>> = flow{
        try {
            emitAll(
                bookDao.getBookByID(id)
                    .map{
                        entity ->
                            if(entity != null){
                                val decodedGenres = Json.decodeFromString<List<Genre>>(entity.genres).toSet()
                                val domain = entity.toDomain().copy(
                                    book = entity.toDomain().book.copy(genres = decodedGenres)
                                )
                                Result.Success(domain)
                            }else{
                                Result.Error(UnknownError("Book with id $id not found"))
                            }
                    }
            )
        }catch (e: Exception){
            emit(Result.Error(UnknownError(e.message ?: "Unknown")))
        }
    }

    override suspend fun saveUserBook(userBook: BookEntry): Result<BookEntry, Error> {
        return try {
            // Parse Set<Genre> to JSON String
            val genresJson = Json.encodeToString(userBook.book.genres.toList())

            val entity = userBook.toEntity().copy(id = 0, genres = genresJson)


            val newId = bookDao.insertBook(entity).toInt()
            val savedBook = userBook.copy(book = userBook.book.copy(id = newId))
            Result.Success(savedBook)
        }catch (e: Exception) {
            Result.Error(UnknownError(e.message ?: "Unknown"))
        }
    }

    override suspend fun updateUserBook(userBook: BookEntry): Result<BookEntry, Error> {
        return try{
            val genresJson = Json.encodeToString(userBook.book.genres.toList())
            val entity = userBook.toEntity().copy(id = userBook.book.id, genres = genresJson)

            bookDao.updateBook(entity)
            Result.Success(userBook)
        }catch (e:Exception) {
            Result.Error(
                UpdatingBookError(
                    e.message ?: "Error while updating book: ${userBook.toEntity().title}"
                )
            )
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