package com.ykenji.cleanarchitecturesample.application.usecase.user.mapper

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.common.UcUser
import com.ykenji.cleanarchitecturesample.domain.model.entity.User

object UserMapper {

    fun toUcUser(user: User): UcUser = UcUser(
        user.id.value,
        user.name.value,
        user.role
    )
}