package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core

import kotlinx.coroutines.flow.Flow

interface UseCase<TInputData : InputData<*>, TOutputData : OutputData> {
    fun handle(inputData: TInputData): TOutputData
    suspend fun suspendHandle(inputData: TInputData): Flow<TOutputData>
}