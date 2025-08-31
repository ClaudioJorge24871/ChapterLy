package com.example.chapterly.domain.repository

import com.example.chapterly.domain.model.Book
import com.example.chapterly.resources.Result
import com.example.chapterly.resources.Error


/**
 * Repository to handle functions involving Books coming from the API
 */
interface BookRepository {

    // Basic search for books
    suspend fun getBooks(): Result<List<Book>,Error>

    // Search API Books by Title
    suspend fun getBooksByTitle(title: String): Result<List<Book>, Error>

    // Search API Book by ISBN
    suspend fun getBookByISBN(isbn: String): Result<Book,Error>
}