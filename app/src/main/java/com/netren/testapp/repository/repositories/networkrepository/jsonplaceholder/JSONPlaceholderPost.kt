package com.netren.testapp.repository.repositories.networkrepository.jsonplaceholder

import com.google.gson.annotations.SerializedName

class JSONPlaceholderPost {
    @SerializedName("userId")
    var userId = 0

    @SerializedName("id")
    var id = 0

    @SerializedName("title")
    var title = ""

    @SerializedName("body")
    var body: String = ""
}