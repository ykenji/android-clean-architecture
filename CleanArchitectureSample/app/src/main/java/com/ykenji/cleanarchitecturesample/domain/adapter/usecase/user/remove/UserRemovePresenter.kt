package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove

import kotlinx.coroutines.flow.Flow

interface UserRemovePresenter {
    val outputFlow: Flow<String?>
    suspend fun output(outputData: UserRemoveOutputData)
}
