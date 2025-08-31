package com.example.chapterly.resources

sealed interface Error

object BookNotFoundError : Error
data class UnknownError(val message: String) : Error
