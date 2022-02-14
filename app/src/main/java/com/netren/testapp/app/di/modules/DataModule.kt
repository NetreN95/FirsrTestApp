package com.netren.testapp.app.di.modules

import com.netren.testapp.repository.MainRepositoryInterface
import com.netren.testapp.repository.mainrepositorymodule.MainRepository
import com.netren.testapp.repository.mainrepositorymodule.repositories.NetworkRepositoryInterface
import com.netren.testapp.repository.mainrepositorymodule.repositories.networkrepository.NetworkRepository
import com.netren.testapp.repository.mainrepositorymodule.repositories.networkrepository.jsonplaceholder.JSONPlaceholderApiService
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val JSON_PLACEHOLDER_URL = "https://jsonplaceholder.typicode.com"

val dataModule = module {

    val ioDispatcher = Dispatchers.IO

    single<MainRepositoryInterface> {
        MainRepository(
            coroutineDispatcher = ioDispatcher,
            networkRepository = get()
        )
    }

    single<NetworkRepositoryInterface> {
        NetworkRepository(
            coroutineDispatcher = ioDispatcher,
            JSONPlaceholderApiService = get()
        )
    }

    single<JSONPlaceholderApiService> {
        Retrofit.Builder()
            .baseUrl(JSON_PLACEHOLDER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JSONPlaceholderApiService::class.java)
    }
}