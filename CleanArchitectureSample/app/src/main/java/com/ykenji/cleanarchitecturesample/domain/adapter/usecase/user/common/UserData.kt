package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.common

import com.ykenji.cleanarchitecturesample.domain.model.user.UserRole

data class UserData(
    val id: String,
    val name: String,
    val role: UserRole,
)