package com.example.chapterly.presentation.mapper

import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.model.Status
import com.example.chapterly.presentation.dto.BookUIDataDTO
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

fun BookUIDataDTO.toDomain(): BookEntry{
    val book = Book(
        id = id,
        isbn = isbn,
        title = title,
        author = author,
        pagination = pagination.toIntOrNull() ?: 0,
        edition = edition?.toIntOrNull(),
        genres = emptyList(),
        publisher = publisher,
        publishYear = publishYear.toIntOrNull() ?: 0,
        coverImageURL = coverImageURL,
    )

    //converting status
    val statusEnum = try{
        Status.valueOf(status.uppercase())
    } catch(e: IllegalArgumentException) {
        Status.TO_READ //fallback
    }

    return BookEntry(
        book = book,
        status = statusEnum,
        purchaseDate = purchaseDate.toLocalDateOrNull(),
        startDate = startDate.toLocalDateOrNull(),
        endDate = endDate.toLocalDateOrNull(),
        currentPage = currentPage.toIntOrNull() ?: 0
    )
}

private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

fun String?.toLocalDateOrNull(): LocalDate? {
    if (this.isNullOrBlank()) return null
    return try{
        LocalDate.parse(this)
    }catch(e: Exception){
        null
    }
}

fun BookEntry.toUIData() = BookUIDataDTO (
    id = book.id,
    isbn = book.isbn,
    title = book.title,
    author = book.author,
    pagination = book.pagination.toString(),
    edition = book.edition?.toString(),
    genres = emptyList(),
    publisher = book.publisher,
    publishYear = book.publishYear.toString(),
    coverImageURL = book.coverImageURL,
    status = status.displayName,
    purchaseDate = purchaseDate.toString(),
    startDate = startDate.toString(),
    endDate = endDate.toString(),
    currentPage = currentPage.toString()
)