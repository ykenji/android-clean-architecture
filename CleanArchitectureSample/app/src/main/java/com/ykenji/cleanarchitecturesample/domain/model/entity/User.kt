package com.ykenji.cleanarchitecturesample.domain.model.entity

import com.ykenji.cleanarchitecturesample.domain.model.value.UserId
import com.ykenji.cleanarchitecturesample.domain.model.value.UserName
import com.ykenji.cleanarchitecturesample.domain.model.value.UserRole

class User(val id: UserId, var name: UserName, val role: UserRole) {

    fun changeName(name: UserName) {
        this.name = name
    }
}