package com.netren.testapp.repository.mainrepositorymodule.repositories.networkrepository

import android.util.Log
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

            Log.d("AAA", "Before NetworkRepository.getPosts(limit = $limit, page = $page)")

            val tmpList = JSONPlaceholderApiService.getPosts(limit = limit, page = page)

            Log.d("AAA", "After NetworkRepository.getPosts(limit = $limit, page = $page)")
            Log.d("AAA", "tmpList.size = ${tmpList.size}")

            return@withContext mapToPostsList(tmpList)
        }

    override suspend fun getPosts(): List<Post> = withContext(coroutineDispatcher) {

        Log.d("AAA", "Before NetworkRepository.getPosts()")

        val tmpList = JSONPlaceholderApiService.getPosts()

        Log.d("AAA", "After NetworkRepository.getPosts()")
        Log.d("AAA", "tmpList.size = ${tmpList.size}")

        return@withContext mapToPostsList(tmpList)
    }

    private fun mapToPostsList(jsonPlaceholderPostsList: List<JSONPlaceholderPost>): List<Post> {
        val postList = arrayListOf<Post>()
        jsonPlaceholderPostsList.forEach {
            postList.add(Post(it))
        }
        return postList
    }
}