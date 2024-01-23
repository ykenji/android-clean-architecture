package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove

interface UserRemovePresenter {
    suspend fun output(outputData: UserRemoveOutputData)
}
