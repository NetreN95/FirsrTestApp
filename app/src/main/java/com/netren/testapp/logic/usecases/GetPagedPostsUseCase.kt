package com.netren.testapp.logic.usecases

import androidx.paging.PagingData
import com.netren.testapp.repository.MainRepositoryInterface
import com.netren.testapp.repository.repositories.models.Post
import kotlinx.coroutines.flow.Flow

class GetPagedPostsUseCase(private val mainRepository: MainRepositoryInterface) {
    fun execute(): Flow<PagingData<Post>> {
        return mainRepository.getPagedPosts()
    }
}