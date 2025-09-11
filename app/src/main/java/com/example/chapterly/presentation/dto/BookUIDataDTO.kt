package com.example.chapterly.presentation.dto

/**
 * DTO for capturing book input from the UI.
 * Mirrors user-facing fields, usually Strings (from TextFields).
 */
data class BookUIDataDTO(
    val id: Int = 0,
    val isbn: String = "",
    val title: String = "",
    val author: String = "",
    val pagination: String = "",
    val edition: String? = null,    //optional
    val genres: List<String> = emptyList(),
    val publisher: String = "",
    val publishYear: String = "",
    val coverImageURL: String = "",
    //BookEntry data
    val status: String = "",
    val purchaseDate: String? = null,
    val startDate: String? = null,
    val endDate: String? = null
)
