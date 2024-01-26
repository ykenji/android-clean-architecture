package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core

import kotlinx.coroutines.flow.Flow

interface Presenter<TOutputData : OutputData> {
    val outputFlow: Flow<TOutputData>
    suspend fun suspendHandle(outputData: TOutputData)
}