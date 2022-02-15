package com.netren.testapp.app.di.modules

import com.netren.testapp.logic.usecases.GetPagedPostsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        GetPagedPostsUseCase(
            mainRepository = get()
        )
    }
}