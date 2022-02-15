package com.netren.testapp.repository.mainrepositorymodule

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.netren.testapp.repository.MainRepositoryInterface
import com.netren.testapp.repository.mainrepositorymodule.pagination.PostsPageLoader
import com.netren.testapp.repository.mainrepositorymodule.pagination.PostsPagingSource
import com.netren.testapp.repository.mainrepositorymodule.repositories.NetworkRepositoryInterface
import com.netren.testapp.repository.mainrepositorymodule.repositories.models.Post
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

private const val PAGE_SIZE = 10

class MainRepository(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val networkRepository: NetworkRepositoryInterface
) : MainRepositoryInterface {

    //function for pagination
    override fun getPagedPosts(): Flow<PagingData<Post>> {
        val postsPageLoader: PostsPageLoader = { pageSize, pageIndex ->
            getPosts(limit = pageSize, page = pageIndex)
        }

        val postsPagingConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        )

        val pagingSourceFactory = { PostsPagingSource(postsPageLoader, PAGE_SIZE) }

        return Pager(
            config = postsPagingConfig,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    private suspend fun getPosts(limit: Int, page: Int): List<Post> =
        withContext(coroutineDispatcher) {
            //To see loading state
            delay(500)
            return@withContext networkRepository.getPosts(limit = limit, page = page)
        }
}