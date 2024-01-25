package com.ykenji.cleanarchitecturesample.application.usecase.user.mapper

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.common.UserData
import com.ykenji.cleanarchitecturesample.domain.model.entity.User
import com.ykenji.cleanarchitecturesample.domain.model.value.UserId
import com.ykenji.cleanarchitecturesample.domain.model.value.UserName
import com.ykenji.cleanarchitecturesample.domain.model.value.UserRole

object UserMapper {

    fun toUserData(user: User): UserData = UserData(
        user.id.value,
        user.name.value,
        user.role
    )

    fun toUser(userData: UserData) = User(
        UserId(userData.id),
        UserName(userData.name),
        userData.role
    )

    fun toUser(userId: String, userName: String, userRole: UserRole) = User(
        UserId(userId),
        UserName(userName),
        userRole
    )

    fun toUserId(userId: String): UserId = UserId(userId)
}