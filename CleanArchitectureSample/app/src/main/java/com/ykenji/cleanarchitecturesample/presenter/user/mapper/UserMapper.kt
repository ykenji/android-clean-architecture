package com.ykenji.cleanarchitecturesample.presenter.user.mapper

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.common.UserData
import com.ykenji.cleanarchitecturesample.presenter.user.model.UiUser

object UserMapper {

    fun toUiUser(userData: UserData) = UiUser(userData.id, userData.name, userData.role.name)
}