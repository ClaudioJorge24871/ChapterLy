package com.example.chapterly.presentation.mapper

import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.model.Status
import com.example.chapterly.presentation.dto.BookUIDataDTO
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun BookUIDataDTO.toDomain(): BookEntry{
    val book = Book(
        isbn = isbn,
        title = title,
        author = author,
        pagination = pagination.toIntOrNull() ?: 0,
        edition = edition?.toIntOrNull(),
        genres = emptyList(),
        publisher = publisher,
        publishYear = publishYear.toIntOrNull() ?: 0,
        coverImageURL = coverImageURL
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
        purchaseDate = purchaseDate.toDateOrNull(),
        startDate = startDate.toDateOrNull(),
        endDate = endDate.toDateOrNull()
    )
}

private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

private fun String?.toDateOrNull(): Date? {
    if (this.isNullOrBlank()) return null
    return try{
        dateFormat.parse(this)
    }catch(e: Exception){
        null
    }
}