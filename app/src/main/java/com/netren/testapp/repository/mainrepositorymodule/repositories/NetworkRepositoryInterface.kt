package com.netren.testapp.repository.mainrepositorymodule.repositories

import com.netren.testapp.repository.mainrepositorymodule.repositories.models.Post

interface NetworkRepositoryInterface {
    //get all of posts
    suspend fun getPosts(): List<Post>
    //get posts by
    //              - limit of posts in one page
    //              - page index
    suspend fun getPosts(limit: Int, page: Int): List<Post>
}