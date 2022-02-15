package com.netren.testapp.repository.mainrepositorymodule.repositories.networkrepository

import com.netren.testapp.repository.mainrepositorymodule.repositories.NetworkRepositoryInterface
import com.netren.testapp.repository.mainrepositorymodule.repositories.models.Post
import com.netren.testapp.repository.mainrepositorymodule.repositories.networkrepository.jsonplaceholder.JSONPlaceholderApiService
import com.netren.testapp.repository.mainrepositorymodule.repositories.networkrepository.jsonplaceholder.JSONPlaceholderPost
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NetworkRepository(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val JSONPlaceholderApiService: JSONPlaceholderApiService
) : NetworkRepositoryInterface {

    override suspend fun getPosts(limit: Int, page: Int): List<Post> =
        withContext(coroutineDispatcher) {

            val resultList = JSONPlaceholderApiService.getPosts(
                limit = limit,
                page = page
            )

            return@withContext mapToPostsList(resultList)
        }

    override suspend fun getPosts(): List<Post> = withContext(coroutineDispatcher) {

        val resultList = JSONPlaceholderApiService.getPosts()

        return@withContext mapToPostsList(resultList)
    }

    private fun mapToPostsList(jsonPlaceholderPostsList: List<JSONPlaceholderPost>): List<Post> {
        val postList = arrayListOf<Post>()
        jsonPlaceholderPostsList.forEach {
            postList.add(Post(it))
        }
        return postList
    }
}