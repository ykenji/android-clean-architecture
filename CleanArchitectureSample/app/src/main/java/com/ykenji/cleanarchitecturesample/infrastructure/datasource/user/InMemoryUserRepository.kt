package com.ykenji.cleanarchitecturesample.infrastructure.datasource.user

import android.content.Context
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.domain.adapter.log.Log
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.model.user.User
import com.ykenji.cleanarchitecturesample.domain.model.user.UserId
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InMemoryUserRepository @Inject constructor(
    @ApplicationContext context: Context,
) : UserRepository {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    private val users: Flow<List<User>>
        get() = _users

    private val userList = ArrayList<User>()

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface InMemoryUserRepositoryEntryPoint {
        fun getServiceProvider(): ServiceProvider
    }

    private val serviceProvider by lazy {
        EntryPointAccessors.fromApplication(context, InMemoryUserRepositoryEntryPoint::class.java)
            .getServiceProvider()
    }

    private val log by lazy {
        serviceProvider.getService(Log::class.java)
    }

    override suspend fun add(user: User) {
        log.d("add")
        userList.add(user)
        _users.value = userList.toList()
    }

    override suspend fun remove(user: User) {
        log.d("remove")
        userList.removeIf {
            it.id.value == user.id.value
        }
        _users.value = userList.toList()
    }

    override fun findAll(): Flow<List<User>> {
        log.d("findAll")
        return users
    }

    override fun find(id: UserId) = flow {
        log.d("find")
        emit(userList.find { it.id.value == id.value })
    }
}