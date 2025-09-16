package com.example.chapterly.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "book_entries")
data class BookEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val isbn: String,
    val title: String,
    val author: String,
    val pagination: Int,
    val currentPage: Int,
    val edition: Int?,
    val genres: String, // stored as JSON, then converted
    val publisher: String,
    val publishYear: Int,
    val coverImageURL: String,
    val status: String,
    val purchaseDate: Long?, // store as timestamp
    val startDate: Long?,
    val endDate: Long?
){
    constructor() : this(
        0,
        "",
        "",
        "",
        0,
        0,
        null,
        "",
        "",
        0,
        "",
        "",
        null,
        null,
        null
    )
}

