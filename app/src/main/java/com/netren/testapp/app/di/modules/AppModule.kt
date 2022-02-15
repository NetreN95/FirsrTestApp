package com.netren.testapp.app.di.modules

import com.netren.testapp.app.view.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel(
            getPagedPostsUseCase = get()
        )
    }
}