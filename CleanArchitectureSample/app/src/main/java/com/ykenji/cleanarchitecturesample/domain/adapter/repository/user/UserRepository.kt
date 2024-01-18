package com.ykenji.cleanarchitecturesample.domain.adapter.repository.user

import com.ykenji.cleanarchitecturesample.domain.model.user.User
import com.ykenji.cleanarchitecturesample.domain.model.user.UserId

interface UserRepository {
    fun add(user: User)
    fun remove(user: User)
    fun findAll(): List<User>
    fun find(id: UserId): User?
}