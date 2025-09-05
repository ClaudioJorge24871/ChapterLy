package com.example.chapterly.presentation.mapper

import com.example.chapterly.data.local.entities.BookEntryEntity
import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.model.Status
import java.util.Date

fun BookEntry.toEntity(): BookEntryEntity = BookEntryEntity(
    isbn = book.isbn,
    title = book.title,
    author = book.author,
    pagination = book.pagination,
    edition = book.edition,
    genres = book.genres.toString(),
    publisher = book.publisher,
    publishYear = book.publishYear,
    coverImageURL = book.coverImageURL,
    status = status.name,
    purchaseDate = purchaseDate?.time,
    startDate = startDate?.time,
    endDate = endDate?.time
)

fun BookEntryEntity.toDomain(): BookEntry = BookEntry(
    book = Book(
        isbn = isbn,
        title = title,
        author = author,
        pagination = pagination,
        edition = edition,
        genres = emptyList(),
        publisher = publisher,
        publishYear = publishYear,
        coverImageURL = coverImageURL
    ),
    status = Status.valueOf(status),
    purchaseDate = purchaseDate?.let { Date(it) },
    startDate = startDate?.let {Date(it)},
    endDate = endDate?.let {Date(it)}
)