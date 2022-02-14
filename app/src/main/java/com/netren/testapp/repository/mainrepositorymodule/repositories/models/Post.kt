package com.netren.testapp.repository.mainrepositorymodule.repositories.models

import com.netren.testapp.repository.mainrepositorymodule.repositories.networkrepository.jsonplaceholder.JSONPlaceholderPost

data class Post(
    var id: Int = 0,
    var userId: Int = 0,
    var title: String = "",
    var body: String = ""
) {
    constructor(jsonPlaceholderPost: JSONPlaceholderPost) : this() {
        this.id = jsonPlaceholderPost.id
        this.userId = jsonPlaceholderPost.userId
        this.title = jsonPlaceholderPost.title
        this.body = jsonPlaceholderPost.body
    }
}