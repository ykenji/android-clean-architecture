package com.ykenji.cleanarchitecturesample.clarchimpl

import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.clarch.invoke.PresenterInvoker
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KCallable
import kotlin.reflect.full.callSuspend

class HandlePresenterInvoker internal constructor(
    private val implementClazz: Class<*>,
    private val provider: ServiceProvider,
) : PresenterInvoker {

    private val suspendHandleMethod: KCallable<*>

    init {
        val methods: Collection<KCallable<*>> = implementClazz.kotlin.members
        suspendHandleMethod = methods.filter { x -> x.name.equals("suspendHandle") }.first()
    }

    override suspend fun <TOutputData : OutputData> suspendInvoke(outputData: TOutputData) {
        val interactor: Any = provider.getService(implementClazz)
        suspendInvoke(interactor, outputData)
    }

    private suspend fun <TOutputData : OutputData> suspendInvoke(
        interactor: Any,
        outputData: TOutputData,
    ) {
        try {
            suspendHandleMethod.callSuspend(interactor, outputData)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        }
    }
}
