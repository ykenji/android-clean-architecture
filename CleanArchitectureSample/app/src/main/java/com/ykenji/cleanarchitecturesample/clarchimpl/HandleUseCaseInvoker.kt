package com.ykenji.cleanarchitecturesample.clarchimpl

import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.clarch.invoke.UseCaseInvoker
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.InputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KCallable
import kotlin.reflect.full.callSuspend

class HandleUseCaseInvoker internal constructor(
    private val implementClazz: Class<*>,
    private val provider: ServiceProvider,
) : UseCaseInvoker {

    private val handleMethod: KCallable<*>
    private val suspendHandleMethod: KCallable<*>

    init {
        val methods: Collection<KCallable<*>> = implementClazz.kotlin.members
        handleMethod = methods.filter { x -> x.name.equals("handle") }.first()
        suspendHandleMethod = methods.filter { x -> x.name.equals("suspendHandle") }.first()
    }

    override fun <TOutputData : OutputData> invoke(inputData: InputData<TOutputData>): TOutputData {
        val interactor: Any = provider.getService(implementClazz)
        return invoke<OutputData>(interactor, inputData) as TOutputData
    }

    override suspend fun <TOutputData : OutputData> suspendInvoke(inputData: InputData<TOutputData>) {
        val interactor: Any = provider.getService(implementClazz)
        suspendInvoke<OutputData>(interactor, inputData)
    }

    private fun <TOutputData : OutputData> invoke(
        interactor: Any,
        inputData: InputData<out TOutputData>,
    ): TOutputData {
        val result = try {
            handleMethod.call(interactor, inputData)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        }
        return result as TOutputData
    }

    private suspend fun <TOutputData : OutputData> suspendInvoke(
        interactor: Any,
        inputData: InputData<out TOutputData>,
    ) {
        try {
            suspendHandleMethod.callSuspend(interactor, inputData)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        }
    }
}
