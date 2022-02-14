package com.netren.testapp.repository

import androidx.paging.PagingData
import com.netren.testapp.repository.mainrepositorymodule.repositories.models.Post
import kotlinx.coroutines.flow.Flow

interface MainRepositoryInterface {
    //function for pagination
    fun getPagedPosts(): Flow<PagingData<Post>>

//    //function for getting posts with limit
//    suspend fun getPosts(limit: Int, page: Int): List<Post>
}