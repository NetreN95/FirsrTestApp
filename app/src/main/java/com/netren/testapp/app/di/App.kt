package com.netren.testapp.app.di

import android.app.Application
import com.netren.testapp.app.di.modules.appModule
import com.netren.testapp.app.di.modules.dataModule
import com.netren.testapp.app.di.modules.domainModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
//            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    domainModule,
                    dataModule
                )
            )
        }
    }
}