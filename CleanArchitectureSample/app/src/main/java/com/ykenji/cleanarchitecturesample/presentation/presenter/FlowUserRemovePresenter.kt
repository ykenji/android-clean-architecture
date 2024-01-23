package com.ykenji.cleanarchitecturesample.presentation.presenter

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemovePresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FlowUserRemovePresenter @Inject constructor() :
    UserRemovePresenter {

    private val _userId: MutableStateFlow<String?> = MutableStateFlow(null)
    override val userId: Flow<String?>
        get() = _userId

    override fun output(outputData: UserRemoveOutputData) {
        _userId.value = outputData.userId
    }
}