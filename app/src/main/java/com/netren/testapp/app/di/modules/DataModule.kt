package com.netren.testapp.app.di.modules

import com.netren.testapp.repository.MainRepositoryInterface
import com.netren.testapp.repository.repositories.MainRepository
import com.netren.testapp.repository.repositories.NetworkRepositoryInterface
import com.netren.testapp.repository.repositories.networkrepository.NetworkRepository
import com.netren.testapp.repository.repositories.networkrepository.jsonplaceholder.JSONPlaceholderApiService
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val JSON_PLACEHOLDER_URL = "https://jsonplaceholder.typicode.com"

val dataModule = module {
    single<MainRepositoryInterface> {
        MainRepository(
            coroutineDispatcher = Dispatchers.IO,
            networkRepository = get()
        )
    }

    single<NetworkRepositoryInterface> {
        NetworkRepository(
            coroutineDispatcher = Dispatchers.IO,
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