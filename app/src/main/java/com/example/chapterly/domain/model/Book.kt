package com.example.chapterly.domain.model

/**
 * Core concepts of a Book
 */
data class Book(
    val id: Int,
    val isbn: String,
    val title: String,
    val author: String,
    val pagination: Int,
    val edition: Int? = null,
    val genres: List<Genre>,
    val publisher: String,
    val publishYear: Int,
    val coverImageURL: String
)