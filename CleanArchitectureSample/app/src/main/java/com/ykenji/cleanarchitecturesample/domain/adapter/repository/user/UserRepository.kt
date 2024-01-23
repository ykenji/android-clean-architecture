package com.ykenji.cleanarchitecturesample.domain.adapter.repository.user

import com.ykenji.cleanarchitecturesample.domain.model.user.User
import com.ykenji.cleanarchitecturesample.domain.model.user.UserId
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun add(user: User)
    suspend fun remove(user: User)
    fun findAll(): Flow<List<User>>
    fun find(id: UserId): Flow<User?>
}