package com.example.chapterly.presentation.mapper

import com.example.chapterly.data.local.entities.BookEntryEntity
import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.model.Status
import java.time.Instant
import java.util.Date

import java.time.LocalDate
import java.time.ZoneId

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
    purchaseDate = purchaseDate?.toEpochMilli(),
    startDate = startDate?.toEpochMilli(),
    endDate = endDate?.toEpochMilli()
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
    purchaseDate = purchaseDate?.toLocalDate(),
    startDate = startDate?.toLocalDate(),
    endDate = endDate?.toLocalDate(),
)


fun LocalDate.toEpochMilli(): Long =
    this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun Long.toLocalDate(): LocalDate =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()