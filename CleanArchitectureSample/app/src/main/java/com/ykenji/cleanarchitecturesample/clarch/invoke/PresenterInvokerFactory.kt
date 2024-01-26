package com.ykenji.cleanarchitecturesample.clarch.invoke

import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider

interface PresenterInvokerFactory {
    fun generate(implementClazz: Class<*>, provider: ServiceProvider): PresenterInvoker
}