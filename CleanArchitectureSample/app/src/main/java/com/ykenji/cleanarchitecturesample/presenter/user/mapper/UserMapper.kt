package com.ykenji.cleanarchitecturesample.presenter.user.mapper

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.common.UcUser
import com.ykenji.cleanarchitecturesample.presenter.user.model.VmUser

object UserMapper {

    fun toVmUser(ucUser: UcUser) = VmUser(ucUser.id, ucUser.name, ucUser.role.name)
}