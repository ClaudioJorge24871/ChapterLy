package com.example.chapterly.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_entries")
data class BookEntryEntity(
    @PrimaryKey val isbn: String,
    val title: String,
    val author: String,
    val pagination: Int,
    val edition: Int?,
    val genres: String, // stored as CSV or JSON, then converted
    val publisher: String,
    val publishYear: Int,
    val coverImageURL: String,
    val status: String,
    val purchaseDate: Long?, // store as timestamp
    val startDate: Long?,
    val endDate: Long?
)
