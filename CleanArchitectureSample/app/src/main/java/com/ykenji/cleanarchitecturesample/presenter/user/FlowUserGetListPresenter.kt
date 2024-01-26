package com.ykenji.cleanarchitecturesample.presenter.user

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListPresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FlowUserGetListPresenter @Inject constructor() : UserGetListPresenter {

    private val _outputFlow =
        MutableStateFlow<UserGetListOutputData>(
            UserGetListOutputData(
                emptyList()
            )
        )
    override val outputFlow: Flow<UserGetListOutputData>
        get() = _outputFlow

    override suspend fun suspendHandle(outputData: UserGetListOutputData) {
        _outputFlow.emit(outputData)
    }
}