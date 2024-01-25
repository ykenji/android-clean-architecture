package com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.mapper

import com.ykenji.cleanarchitecturesample.domain.model.entity.User
import com.ykenji.cleanarchitecturesample.domain.model.value.UserId
import com.ykenji.cleanarchitecturesample.domain.model.value.UserName
import com.ykenji.cleanarchitecturesample.domain.model.value.UserRole
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.entity.DbUser

object UserMapper {

    fun toDbUser(user: User): DbUser = DbUser(
        user.id.value,
        user.name.value,
        user.role.name
    )

    fun toUser(dbUser: DbUser) = User(
        UserId(dbUser.id),
        UserName(dbUser.name),
        when (dbUser.role) {
            UserRole.ADMIN.name -> UserRole.ADMIN
            else -> UserRole.MEMBER
        }
    )
}