package com.ykenji.cleanarchitecturesample.adapter.presenter

import com.ykenji.cleanarchitecturesample.adapter.viewmodel.UiUser
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListPresenter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FlowUserGetListPresenter @Inject constructor() : UserGetListPresenter {

    private val _users: MutableStateFlow<List<UiUser>> = MutableStateFlow(emptyList())
    val users: StateFlow<List<UiUser>>
        get() = _users

    override suspend fun output(outputData: UserGetListOutputData) {
        _users.value = outputData.users.map { UiUser(it.id, it.name, it.role.name) }
    }
}