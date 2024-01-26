package com.ykenji.cleanarchitecturesample.clarch

import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.clarch.invoke.PresenterInvokerFactory
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.Presenter

class PresenterBusBuilder(
    private val provider: ServiceProvider,
    private val bus: PresenterBus = PresenterBus(),
) {
    fun <
            TOutputData : OutputData,
            TPresenter : Presenter<out OutputData>,
            > register(
        outputDataClazz: Class<TOutputData>,
        presenterClazz: Class<TPresenter>,
    ) {
        bus.register(outputDataClazz, presenterClazz)
    }

    fun build(invokerFactory: PresenterInvokerFactory): PresenterBus {
        bus.setup(provider, invokerFactory)
        return bus
    }
}