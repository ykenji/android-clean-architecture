package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.common.UserData
import kotlinx.coroutines.flow.Flow

interface UserGetListPresenter {

    val userList: Flow<List<UserData>>

    fun output(outputData: UserGetListOutputData)
}
