package com.ykenji.cleanarchitecturesample.clarch.inject

interface ServiceProvider {
    fun <T> getService(type: Class<T>?): T
}