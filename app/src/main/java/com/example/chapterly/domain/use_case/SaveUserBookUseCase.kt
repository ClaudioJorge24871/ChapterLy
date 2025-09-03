package com.example.chapterly.domain.use_case

import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.model.Book
import com.example.chapterly.domain.repository.BookRepository
import com.example.chapterly.domain.repository.UserLibraryRepository
import com.example.chapterly.resources.Error
import com.example.chapterly.resources.Result
import javax.inject.Inject

class SaveUserBookUseCase @Inject constructor(
    private val userLibraryRepository: UserLibraryRepository,
    //private val bookRepository: BookRepository
){
    suspend operator fun invoke(userBook: BookEntry): Result<BookEntry, Error> {
        //val fetchResult = bookRepository.getBookByISBN(userBook.book.isbn)
        return userLibraryRepository.saveUserBook(userBook)
        /** Used for when the BookRepository is implemented. Not obligatory for the Save feature
        return when (fetchResult) {
            is Result.Success -> {
                val fetchedBook: Book = fetchResult.data
                val entryToSave = userBook.copy(book = fetchedBook)
                userLibraryRepository.saveUserBook(entryToSave)
            }
            is Result.Error -> {
                // handle error or save original userBook
                //TODO: Yet to be implemented or reviewed
                userLibraryRepository.saveUserBook(userBook)
            }
        }
        */
    }


}