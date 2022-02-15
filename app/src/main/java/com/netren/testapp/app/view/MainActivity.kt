package com.netren.testapp.app.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.netren.testapp.app.adapters.DefaultLoadStateAdapter
import com.netren.testapp.app.adapters.PostsAdapter
import com.netren.testapp.app.adapters.TryAgainAction
import com.netren.testapp.app.adapters.simpleScan
import com.netren.testapp.app.view.viewmodel.MainViewModel
import com.netren.testapp.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
class MainActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupPostsList()
        setupSwipeToRefresh()
    }

    private fun setupPostsList() {
        val adapter = PostsAdapter()

        // in case of loading errors this callback is called when you tap the 'Try Again' button
        val tryAgainAction: TryAgainAction = { adapter.retry() }

        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)

        // combined adapter which shows both the list of users + footer indicator when loading pages
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        binding.postsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.postsRecyclerView.adapter = adapterWithLoadState
        (binding.postsRecyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations =
            false

        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
            binding.swipeRefreshLayout,
            tryAgainAction
        )

        observeUsers(adapter)
        observeLoadState(adapter)

        handleListVisibility(adapter)
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            vm.refresh()
        }
    }

    private fun observeUsers(adapter: PostsAdapter) {
        lifecycleScope.launch {
            vm.postsFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeLoadState(adapter: PostsAdapter) {
        // you can also use adapter.addLoadStateListener
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                // main indicator in the center of the screen
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun handleListVisibility(adapter: PostsAdapter) = lifecycleScope.launch {
        // list should be hidden if an error is displayed OR if items are being loaded after the error:
        // (current state = Error) OR (prev state = Error)
        //   OR
        // (before prev state = Error, prev state = NotLoading, current state = Loading)
        getRefreshLoadStateFlow(adapter)
            .simpleScan(count = 3)
            .collectLatest { (beforePrevious, previous, current) ->
                binding.postsRecyclerView.isInvisible = current is LoadState.Error
                        || previous is LoadState.Error
                        || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                        && current is LoadState.Loading)
            }
    }

    private fun getRefreshLoadStateFlow(adapter: PostsAdapter): Flow<LoadState> {
        return adapter.loadStateFlow
            .map { it.refresh }
    }
}

