package com.bcoding.readtracker.app

import android.app.Application
import com.bcoding.readtracker.di.database.databaseModule
import com.bcoding.readtracker.di.network.networkModule
import com.bcoding.readtracker.di.repository.repositoryModule
import com.bcoding.readtracker.di.viewmodel.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                repositoryModule,
                viewModelModule,
                databaseModule
            )
        }
    }
}
