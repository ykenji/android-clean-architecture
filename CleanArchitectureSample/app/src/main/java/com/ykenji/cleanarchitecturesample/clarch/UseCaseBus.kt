package com.ykenji.cleanarchitecturesample.clarch

import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.clarch.invoke.UseCaseInvoker
import com.ykenji.cleanarchitecturesample.clarch.invoke.UseCaseInvokerFactory
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.InputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData


class UseCaseBus {
    private val handlerTypes: HashMap<Class<*>, Class<*>?> = HashMap()
    private val invokers: HashMap<Class<*>, UseCaseInvoker?> = HashMap()

    private lateinit var invokerFactory: UseCaseInvokerFactory

    private lateinit var provider: ServiceProvider

    fun <TInputData : InputData<TOutputData>, TOutputData : OutputData>
            handle(inputData: TInputData): TOutputData? {
        return getInvoker(inputData)?.invoke(inputData)
    }

    fun setup(provider: ServiceProvider, invokerFactory: UseCaseInvokerFactory) {
        this.provider = provider
        this.invokerFactory = invokerFactory
    }

    fun <TInputData, TUseCase> register(
        inputDataClazz: Class<TInputData>,
        useCaseClazz: Class<TUseCase>,
    ) {
        handlerTypes[inputDataClazz] = useCaseClazz
    }

    private fun <TOutputData : OutputData> getInvoker(inputData: InputData<TOutputData>): UseCaseInvoker? {

        val inputDataClazz = inputData.javaClass

        var invoker = invokers.getOrDefault(inputDataClazz, null)
        if (invoker != null) {
            return invoker
        }
        val handlerClazz = handlerTypes.getOrDefault(inputDataClazz, null)
            ?: throw RuntimeException("not registered")

        invoker = invokerFactory.generate(handlerClazz, provider)
        invokers[inputDataClazz] = invoker
        return invoker
    }
}