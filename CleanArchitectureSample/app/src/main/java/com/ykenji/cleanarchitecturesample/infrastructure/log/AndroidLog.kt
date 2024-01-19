package com.ykenji.cleanarchitecturesample.infrastructure.log

import com.ykenji.cleanarchitecturesample.domain.adapter.log.Log
import javax.inject.Inject

class AndroidLog @Inject constructor() : Log {

    companion object {
        const val TAG = "CleanArchitectureSample"
    }

    override fun init() = Unit

    override fun e(msg: String) {
        android.util.Log.e(TAG, msg)
    }

    override fun w(msg: String) {
        android.util.Log.w(TAG, msg)
    }

    override fun i(msg: String) {
        android.util.Log.i(TAG, msg)
    }

    override fun d(msg: String) {
        android.util.Log.d(TAG, msg)
    }

    override fun v(msg: String) {
        android.util.Log.v(TAG, msg)
    }
}