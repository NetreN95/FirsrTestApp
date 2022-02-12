package com.netren.testapp.app.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.netren.testapp.logic.usecases.GetPagedPostsUseCase
import com.netren.testapp.repository.repositories.models.Post
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(FlowPreview::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class MainViewModel (private val getPagedPostsUseCase: GetPagedPostsUseCase) : ViewModel() {

    val postsFlow: Flow<PagingData<Post>>

    private val searchBy = MutableLiveData("")

    init {
        postsFlow = searchBy.asFlow()
            // if user types text too quickly -> filtering intermediate values to avoid excess loads
//            .debounce(500)
            .flatMapLatest {
                getPagedPostsUseCase.execute()
            }
            // always use cacheIn operator for flows returned by Pager. Otherwise exception may be thrown
            // when 1) refreshing/invalidating or 2) subscribing to the flow more than once.
            .cachedIn(viewModelScope)
    }

    fun refresh() {
        this.searchBy.postValue(this.searchBy.value)
    }
}