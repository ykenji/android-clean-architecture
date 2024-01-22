package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.common.UserData
import kotlinx.coroutines.flow.Flow

interface UserGetListPresenter {

    val users: Flow<List<UserData>>

    fun output(outputData: UserGetListOutputData)
}
