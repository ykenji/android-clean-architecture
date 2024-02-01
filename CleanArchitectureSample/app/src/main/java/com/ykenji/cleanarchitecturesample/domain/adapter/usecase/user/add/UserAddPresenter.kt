package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add

import kotlinx.coroutines.flow.Flow

interface UserAddPresenter {
    val outputFlow: Flow<String?>
    suspend fun output(outputData: UserAddOutputData)
}
