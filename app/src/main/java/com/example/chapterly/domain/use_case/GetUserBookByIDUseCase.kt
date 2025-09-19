package com.example.chapterly.domain.use_case

import com.example.chapterly.domain.model.BookEntry
import com.example.chapterly.domain.repository.UserLibraryRepository
import javax.inject.Inject
import com.example.chapterly.resources.Result
import com.example.chapterly.resources.Error
import kotlinx.coroutines.flow.Flow

class GetUserBookByIDUseCase @Inject constructor(
    private val userLibraryRepository: UserLibraryRepository
){
    operator fun invoke(id: Int): Flow<Result<BookEntry, Error>> {
        return userLibraryRepository.getBookByID(id)
    }
}