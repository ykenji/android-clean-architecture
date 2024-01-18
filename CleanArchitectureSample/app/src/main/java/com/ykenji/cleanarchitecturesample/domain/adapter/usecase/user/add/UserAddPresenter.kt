package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add

import kotlinx.coroutines.flow.Flow

interface UserAddPresenter {

    val userId: Flow<String?>

    fun output(outputData: UserAddOutputData)
}
