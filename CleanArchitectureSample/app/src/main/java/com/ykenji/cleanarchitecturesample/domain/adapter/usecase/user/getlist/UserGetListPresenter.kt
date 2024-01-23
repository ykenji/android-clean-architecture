package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist

interface UserGetListPresenter {
    suspend fun output(outputData: UserGetListOutputData)
}
