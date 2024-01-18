package com.ykenji.cleanarchitecturesample.infrastructure.log

import com.ykenji.cleanarchitecturesample.domain.adapter.log.Log
import timber.log.Timber
import javax.inject.Inject

class TimberLog @Inject constructor() : Log {
    override fun init() = Timber.plant(CustomDebugTree("CleanArchitectureSample"))

    override fun e(msg: String) = Timber.e(msg)

    override fun w(msg: String) = Timber.w(msg)

    override fun i(msg: String) = Timber.i(msg)

    override fun d(msg: String) = Timber.d(msg)

    override fun v(msg: String) = Timber.v(msg)
}