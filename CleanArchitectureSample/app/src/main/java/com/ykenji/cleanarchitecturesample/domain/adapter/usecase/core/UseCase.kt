package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core

interface UseCase<TInputData : InputData<*>, TOutputData : OutputData> {
    fun handle(inputData: TInputData): TOutputData
    suspend fun suspendHandle(inputData: TInputData)
}