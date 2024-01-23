package com.ykenji.cleanarchitecturesample.adapter.presenter

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddPresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class FlowUserAddPresenter @Inject constructor() : UserAddPresenter {

    private val _userId = MutableSharedFlow<String>()
    val userId: Flow<String>
        get() = _userId

    override suspend fun output(outputData: UserAddOutputData) {
        outputData.userId?.let {
            _userId.emit(it)
        }
    }
}