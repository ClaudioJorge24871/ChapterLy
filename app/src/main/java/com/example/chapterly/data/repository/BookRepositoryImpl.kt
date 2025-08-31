package com.example.chapterly.data.repository

import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.repository.BookRepository
import com.example.chapterly.resources.Result
import com.example.chapterly.resources.Error


class BookRepositoryImpl : BookRepository{
    private val books = mutableListOf<Book>()

    override suspend fun getBooks(): Result<List<Book>, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun getBooksByTitle(title: String): Result<List<Book>, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun getBookByISBN(isbn: String): Result<Book, Error> {
        TODO("Not yet implemented")
    }
}