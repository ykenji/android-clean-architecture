package com.ykenji.cleanarchitecturesample.clarchimpl

import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.clarch.invoke.PresenterInvoker
import com.ykenji.cleanarchitecturesample.clarch.invoke.PresenterInvokerFactory

class HandlePresenterInvokerFactory : PresenterInvokerFactory {
    override fun generate(implementClazz: Class<*>, provider: ServiceProvider): PresenterInvoker {
        return HandlePresenterInvoker(implementClazz, provider)
    }
}