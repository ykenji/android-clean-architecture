package com.ykenji.cleanarchitecturesample.clarchimpl

import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.clarch.invoke.UseCaseInvoker
import com.ykenji.cleanarchitecturesample.clarch.invoke.UseCaseInvokerFactory

class HandleUseCaseInvokerFactory : UseCaseInvokerFactory {
    override fun generate(implementClazz: Class<*>, provider: ServiceProvider): UseCaseInvoker {
        return HandleUseCaseInvoker(implementClazz, provider)
    }
}