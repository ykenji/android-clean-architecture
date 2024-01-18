package com.ykenji.cleanarchitecturesample.infrastructure.log

import android.util.Log
import timber.log.Timber

class CustomDebugTree(val appName: String) : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val thread = Throwable().stackTrace
        if (thread.size >= 5) {
            val stack = thread[5]
            val fileName = stack.fileName
            val msg = "(" + fileName + ":" + stack.lineNumber + ") " + message
            Log.println(priority, appName, msg)
        } else {
            Log.println(priority, appName, message)
        }
    }
}