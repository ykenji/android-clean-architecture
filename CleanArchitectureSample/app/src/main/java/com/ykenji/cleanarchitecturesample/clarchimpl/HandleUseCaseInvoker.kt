package com.ykenji.cleanarchitecturesample.clarchimpl

import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.clarch.invoke.UseCaseInvoker
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.InputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.Arrays
import java.util.stream.Stream

class HandleUseCaseInvoker internal constructor(
    private val implementClazz: Class<*>,
    private val provider: ServiceProvider,
) : UseCaseInvoker {

    private val handleMethod: Method

    init {
        val methods: Stream<Method> = Arrays.stream(implementClazz.methods)
        handleMethod = methods.filter { x -> x.getName().equals("handle") }.findFirst().get()
    }

    override fun <TOutputData : OutputData> invoke(inputData: InputData<TOutputData>): TOutputData {
        val interactor: Any = provider.getService(implementClazz)
        val outputData = invoke<OutputData>(interactor, inputData)
        return outputData as TOutputData
    }

    private operator fun <TOutputData : OutputData> invoke(
        interactor: Any,
        inputData: InputData<out TOutputData>,
    ): TOutputData {
        val result = try {
            handleMethod.invoke(interactor, inputData)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        }
        return result as TOutputData
    }
}
