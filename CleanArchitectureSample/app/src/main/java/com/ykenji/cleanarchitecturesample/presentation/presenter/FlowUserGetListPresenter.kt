package com.ykenji.cleanarchitecturesample.presentation.presenter

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.common.UserData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListPresenter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FlowUserGetListPresenter @Inject constructor() :
    UserGetListPresenter {

    private val _userList: MutableStateFlow<List<UserData>> = MutableStateFlow(emptyList())
    override val userList: Flow<List<UserData>>
        get() = _userList

    override fun output(outputData: UserGetListOutputData) {
        _userList.value = outputData.users
    }
}