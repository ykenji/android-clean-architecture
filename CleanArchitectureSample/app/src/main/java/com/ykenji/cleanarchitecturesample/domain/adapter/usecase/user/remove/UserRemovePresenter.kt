package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove

import kotlinx.coroutines.flow.Flow

interface UserRemovePresenter {

    val userId: Flow<String?>


    fun output(outputData: UserRemoveOutputData)
}
