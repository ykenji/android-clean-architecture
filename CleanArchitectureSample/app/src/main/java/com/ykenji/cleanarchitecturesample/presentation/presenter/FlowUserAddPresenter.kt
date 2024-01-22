package com.ykenji.cleanarchitecturesample.presentation.presenter

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddPresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FlowUserAddPresenter @Inject constructor() :
    UserAddPresenter {

    private val _userId: MutableStateFlow<String?> = MutableStateFlow(null)
    override val userId: Flow<String?>
        get() = _userId

    override fun output(outputData: UserAddOutputData) {
        _userId.value = outputData.userId
    }
}