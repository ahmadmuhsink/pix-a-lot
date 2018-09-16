package com.amk.pixalot.arch

import android.app.Application
import com.amk.pixalot.di.networkModule
import com.amk.pixalot.di.sourceModule
import com.amk.pixalot.di.photoListModule
import org.koin.android.ext.android.startKoin

class PixALotApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
                this,
                listOf(
                        networkModule,
                        sourceModule,
                        photoListModule
                )
        )
    }
}