package com.ykenji.cleanarchitecturesample.infrastructure.datasource.datastore.mapper

import com.ykenji.cleanarchitecturesample.domain.model.entity.User
import com.ykenji.cleanarchitecturesample.domain.model.value.UserId
import com.ykenji.cleanarchitecturesample.domain.model.value.UserName
import com.ykenji.cleanarchitecturesample.domain.model.value.UserRole

object UserMapper {

    fun toString(user: User) = "${user.id.value}#${user.name.value}#${user.role}"

    fun toUser(string: String) =
        string.split("#").let {
            User(
                UserId(it[0]),
                UserName(it[1]),
                when (it[2]) {
                    UserRole.ADMIN.name -> UserRole.ADMIN
                    else -> UserRole.MEMBER
                }
            )
        }
}