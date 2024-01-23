package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add

interface UserAddPresenter {
    suspend fun output(outputData: UserAddOutputData)
}
