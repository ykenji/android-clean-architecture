package com.ykenji.cleanarchitecturesample.clarch.invoke

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData

interface PresenterInvoker {
    suspend fun <TOutputData : OutputData> suspendInvoke(outputData: TOutputData)
}