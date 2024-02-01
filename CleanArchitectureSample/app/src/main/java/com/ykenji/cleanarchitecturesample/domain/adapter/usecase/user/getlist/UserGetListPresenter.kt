package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist

import com.ykenji.cleanarchitecturesample.presenter.user.model.VmUser
import kotlinx.coroutines.flow.Flow

interface UserGetListPresenter {
    val outputFlow: Flow<List<VmUser>>
    suspend fun output(outputData: UserGetListOutputData)
}
