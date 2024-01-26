package com.ykenji.cleanarchitecturesample

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * How to run instrumented tests
 * $ ./gradlew assembleAndroidTest
 * $ adb install app-debug-androidTest.apk
 * $ adb shell pm list instrumentation
 * instrumentation:com.ykenji.cleanarchitecturesample.test/com.ykenji.cleanarchitecturesample.CustomTestRunner (target=com.ykenji.cleanarchitecturesample)
 * $ adb shell am instrument -w com.ykenji.cleanarchitecturesample.test/com.ykenji.cleanarchitecturesample.CustomTestRunner
 */
class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}