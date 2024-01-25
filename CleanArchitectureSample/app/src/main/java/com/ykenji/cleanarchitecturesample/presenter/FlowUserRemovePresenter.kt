package com.ykenji.cleanarchitecturesample.presenter

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemovePresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class FlowUserRemovePresenter @Inject constructor() : UserRemovePresenter {

    private val _userId = MutableSharedFlow<String>()
    val userId: Flow<String>
        get() = _userId

    override suspend fun output(outputData: UserRemoveOutputData) {
        outputData.userId?.let {
            _userId.emit(it)
        }
    }
}