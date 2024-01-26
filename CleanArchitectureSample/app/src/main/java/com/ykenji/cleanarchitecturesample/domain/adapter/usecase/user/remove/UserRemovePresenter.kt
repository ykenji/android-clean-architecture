package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove

import kotlinx.coroutines.flow.Flow

interface UserRemovePresenter {
    val outputFlow: Flow<UserRemoveOutputData>
    suspend fun output(outputData: UserRemoveOutputData)
}
