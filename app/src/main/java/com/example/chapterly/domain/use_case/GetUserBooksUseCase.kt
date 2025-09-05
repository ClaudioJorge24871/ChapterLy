package com.example.chapterly.domain.use_case

import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.repository.BookRepository
import com.example.chapterly.domain.repository.UserLibraryRepository
import com.example.chapterly.resources.Result
import com.example.chapterly.resources.Error
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserBooksUseCase @Inject constructor(
    private val userLibraryRepository: UserLibraryRepository
){
    operator fun invoke(): Flow<Result<List<BookEntry>, Error>> {
        return userLibraryRepository.getUserBooks()
    }
}