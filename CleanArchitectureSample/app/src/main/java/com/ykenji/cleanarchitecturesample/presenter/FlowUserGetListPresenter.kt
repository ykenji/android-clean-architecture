package com.ykenji.cleanarchitecturesample.presenter

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListPresenter
import com.ykenji.cleanarchitecturesample.presenter.mapper.UserMapper
import com.ykenji.cleanarchitecturesample.presenter.model.UiUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FlowUserGetListPresenter @Inject constructor() : UserGetListPresenter {

    private val _users: MutableStateFlow<List<UiUser>> = MutableStateFlow(emptyList())
    val users: StateFlow<List<UiUser>>
        get() = _users

    override suspend fun output(outputData: UserGetListOutputData) {
        _users.emit(outputData.users.map { UserMapper.toUiUser(it) })
    }
}