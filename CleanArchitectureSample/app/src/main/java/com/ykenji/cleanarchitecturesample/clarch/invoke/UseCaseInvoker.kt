package com.ykenji.cleanarchitecturesample.clarch.invoke

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.InputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData

interface UseCaseInvoker {
    fun <TOutputData : OutputData> invoke(inputData: InputData<TOutputData>): TOutputData
    suspend fun <TOutputData : OutputData> suspendInvoke(inputData: InputData<TOutputData>)
}