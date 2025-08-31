package com.example.chapterly.domain.model

import java.util.Date

/**
 * Entry of a Book
 * This data class corresponds to a Book,
 * but with extra properties a User enters when "saving" a Book
 *
 * This class doesn't extend Book because, although it would have less boilerplate,
 * its semantically wrong to call it an heir of Book. So we wrap it
 */
data class BookEntry(
    val book: Book,
    val purchaseDate: Date? = null,
    val startDate: Date? = null,
    val endDate: Date? = null,

)
