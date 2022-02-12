package com.netren.testapp.repository.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.netren.testapp.repository.MainRepositoryInterface
import com.netren.testapp.repository.repositories.models.Post
import com.netren.testapp.repository.repositories.pagination.PostsPageLoader
import com.netren.testapp.repository.repositories.pagination.PostsPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

private const val PAGE_SIZE = 10

class MainRepository(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val networkRepository: NetworkRepositoryInterface
) : MainRepositoryInterface {

    //function for pagination
    override fun getPagedPosts(): Flow<PagingData<Post>> {
        val loader: PostsPageLoader = { pageSize, pageIndex ->
            getPosts(limit = pageSize, page = pageIndex)
        }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PostsPagingSource(loader, PAGE_SIZE) }
        ).flow
    }

    private suspend fun getPosts(limit: Int, page: Int): List<Post> =
        withContext(coroutineDispatcher) {
            val offset = limit * page

            return@withContext networkRepository.getPosts(limit = offset, page = 1)
        }
}