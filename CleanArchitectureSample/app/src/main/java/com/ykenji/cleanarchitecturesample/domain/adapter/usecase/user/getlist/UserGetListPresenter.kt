package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist

import kotlinx.coroutines.flow.Flow

interface UserGetListPresenter {
    val outputFlow: Flow<UserGetListOutputData>
    suspend fun output(outputData: UserGetListOutputData)
}
