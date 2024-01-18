package com.ykenji.cleanarchitecturesample.clarch.invoke

import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider

interface UseCaseInvokerFactory {
    fun generate(implementClazz: Class<*>, provider: ServiceProvider): UseCaseInvoker
}