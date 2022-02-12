package com.netren.testapp.repository.repositories.networkrepository.jsonplaceholder

import retrofit2.http.GET
import retrofit2.http.Query

interface JSONPlaceholderApiService {
    //get all of posts
    @GET("posts")
    suspend fun getPosts(): List<JSONPlaceholderPost>

    //get posts by
    //              - limit of posts in one page
    //              - page index
    @GET("posts")
    suspend fun getPosts(
        @Query("_limit") limit: Int,
        @Query("_page") page: Int
    ): List<JSONPlaceholderPost>
}