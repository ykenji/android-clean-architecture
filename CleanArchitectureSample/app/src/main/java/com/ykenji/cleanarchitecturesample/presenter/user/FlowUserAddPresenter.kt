package com.ykenji.cleanarchitecturesample.presenter.user

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddPresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class FlowUserAddPresenter @Inject constructor() : UserAddPresenter {

    private val _outputFlow = MutableSharedFlow<UserAddOutputData>()
    override val outputFlow: Flow<UserAddOutputData>
        get() = _outputFlow

    override suspend fun output(outputData: UserAddOutputData) {
        _outputFlow.emit(outputData)
    }
}