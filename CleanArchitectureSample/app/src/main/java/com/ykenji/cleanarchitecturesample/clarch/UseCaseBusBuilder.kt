package com.ykenji.cleanarchitecturesample.clarch

import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.clarch.invoke.UseCaseInvokerFactory
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.InputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.UseCase

class UseCaseBusBuilder(
    private val provider: ServiceProvider,
    private val bus: UseCaseBus = UseCaseBus(),
) {
    fun <
            TInputData : InputData<out OutputData>,
            TUseCase : UseCase<out InputData<out OutputData>, out OutputData>,
            > register(
        inputDataClazz: Class<TInputData>,
        useCaseClazz: Class<TUseCase>,
    ) {
        bus.register(inputDataClazz, useCaseClazz)
    }

    fun build(invokerFactory: UseCaseInvokerFactory): UseCaseBus {
        bus.setup(provider, invokerFactory)
        return bus
    }
}