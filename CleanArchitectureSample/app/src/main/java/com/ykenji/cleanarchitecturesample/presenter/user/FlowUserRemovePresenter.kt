package com.ykenji.cleanarchitecturesample.presenter.user

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemovePresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class FlowUserRemovePresenter @Inject constructor() : UserRemovePresenter {
    private val _outputFlow = MutableSharedFlow<String?>()
    override val outputFlow: Flow<String?>
        get() = _outputFlow

    override suspend fun output(outputData: UserRemoveOutputData) {
        _outputFlow.emit(outputData.userId)
    }
}