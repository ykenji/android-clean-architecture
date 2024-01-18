package com.ykenji.cleanarchitecturesample.clarch.invoke

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.InputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData

interface UseCaseInvoker {
    operator fun <TOutputData : OutputData> invoke(inputData: InputData<TOutputData>): TOutputData
}