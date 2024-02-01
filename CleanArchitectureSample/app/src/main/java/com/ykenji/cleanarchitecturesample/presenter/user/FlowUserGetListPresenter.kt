package com.ykenji.cleanarchitecturesample.presenter.user

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListPresenter
import com.ykenji.cleanarchitecturesample.presenter.user.mapper.UserMapper
import com.ykenji.cleanarchitecturesample.presenter.user.model.VmUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FlowUserGetListPresenter @Inject constructor() : UserGetListPresenter {

    private val _outputFlow = MutableStateFlow<List<VmUser>>(emptyList())
    override val outputFlow: Flow<List<VmUser>>
        get() = _outputFlow

    override suspend fun output(outputData: UserGetListOutputData) {
        _outputFlow.emit(outputData.users.map {
            UserMapper.toVmUser(it)
        })
    }
}