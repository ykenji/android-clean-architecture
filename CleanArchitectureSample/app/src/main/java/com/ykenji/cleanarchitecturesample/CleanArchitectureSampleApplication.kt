package com.ykenji.cleanarchitecturesample

import android.app.Application
import com.ykenji.cleanarchitecturesample.domain.adapter.log.Log
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CleanArchitectureSampleApplication : Application() {

    @Inject
    lateinit var log: Log

    override fun onCreate() {
        super.onCreate()
        log.init()
    }
}
