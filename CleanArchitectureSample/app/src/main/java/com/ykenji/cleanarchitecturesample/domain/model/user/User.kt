package com.ykenji.cleanarchitecturesample.domain.model.user

class User(val id: UserId, var name: UserName, val role: UserRole) {

    fun changeName(name: UserName) {
        this.name = name
    }
}