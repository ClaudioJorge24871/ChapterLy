package com.example.chapterly.domain.repository

import com.example.chapterly.domain.model.Book

/**
 * Repository to handle functions involving Books coming from the API
 */
interface BookRepository {

    // Basic search for books
    suspend fun getBooks(): List<Book>

    // Search API Books by Title
    suspend fun getBooksByTitle(title: String): List<Book>

    // Search API Book by ISBN
    suspend fun getBookByISBN(isbn: String): Book?
}