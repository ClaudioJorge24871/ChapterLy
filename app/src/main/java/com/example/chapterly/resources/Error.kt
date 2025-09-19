package com.example.chapterly.resources

sealed interface Error

data class BookNotFoundError(val message: String) : Error
data class UnknownError(val message: String) : Error
data class UpdatingBookError(val message: String): Error
