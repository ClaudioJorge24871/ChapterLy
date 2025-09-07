package com.example.chapterly.resources

sealed interface Error

object BookNotFoundError : Error
data class UnknownError(val message: String) : Error
data class UpdatingBookError(val message: String): Error