package com.netren.testapp.repository.mainrepositorymodule.pagination

import com.netren.testapp.repository.mainrepositorymodule.repositories.models.Post

typealias PostsPageLoader = suspend (
    pageSize: Int,
    pageIndex: Int
) -> List<Post>