package com.ykenji.cleanarchitecturesample.clarch

import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.clarch.invoke.PresenterInvoker
import com.ykenji.cleanarchitecturesample.clarch.invoke.PresenterInvokerFactory
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData

class PresenterBus {
    private val handlerTypes: HashMap<Class<*>, Class<*>?> = HashMap()
    private val invokers: HashMap<Class<*>, PresenterInvoker?> = HashMap()

    private lateinit var invokerFactory: PresenterInvokerFactory

    private lateinit var provider: ServiceProvider

    suspend fun <TOutputData : OutputData> suspendHandle(outputData: TOutputData) =
        getInvoker(outputData)?.suspendInvoke(outputData)

    fun setup(provider: ServiceProvider, invokerFactory: PresenterInvokerFactory) {
        this.provider = provider
        this.invokerFactory = invokerFactory
    }

    fun <TOutputData, TPresenter> register(
        outputDataClazz: Class<TOutputData>,
        presenterClazz: Class<TPresenter>,
    ) {
        handlerTypes[outputDataClazz] = presenterClazz
    }

    private fun <TOutputData : OutputData> getInvoker(outputData: TOutputData): PresenterInvoker? {

        val outputDataClazz = outputData.javaClass

        var invoker = invokers.getOrDefault(outputDataClazz, null)
        if (invoker != null) {
            return invoker
        }
        val handlerClazz = handlerTypes.getOrDefault(outputDataClazz, null)
            ?: throw RuntimeException("not registered")

        invoker = invokerFactory.generate(handlerClazz, provider)
        invokers[outputDataClazz] = invoker
        return invoker
    }
}