package com.example.chapterly.domain.model

/**
 * Books genres
 */
enum class Genre(val displayName: String) {
    FICTION("Fiction"),
    NON_FICTION("Non-fiction"),
    FANTASY("Fantasy"),
    SCIENCE_FICTION("Science fiction"),
    MYSTERY("Mystery"),
    BIOGRAPHY("Biography"),
    HISTORY("History"),
    ROMANCE("Romance"),
    THRILLER("Thriller"),
    CHILDREN("Children")
    //TODO change from enum to add new genres if wanted
}