package com.ykenji.cleanarchitecturesample.domain.adapter.log

interface Log {
    fun init()
    fun e(msg: String)
    fun w(msg: String)
    fun i(msg: String)
    fun d(msg: String)
    fun v(msg: String)
}