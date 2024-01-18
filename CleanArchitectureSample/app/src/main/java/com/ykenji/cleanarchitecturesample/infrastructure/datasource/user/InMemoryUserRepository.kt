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
import javax.inject.Inject

class InMemoryUserRepository @Inject constructor(
    @ApplicationContext context: Context,
) : UserRepository {

    private val _users = MutableStateFlow<Array<User>>(emptyArray())
    override val users: Flow<Array<User>>
        get() = _users

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

    private val userList = ArrayList<User>()

    override fun add(user: User) {
        log.d("add")
        print(user)
        userList.add(user)
        _users.value = userList.toTypedArray()
    }

    override fun remove(user: User) {
        log.d("remove")
        print(user)
        userList.remove(user)
        _users.value = userList.toTypedArray()
    }

    override fun findAll(): List<User> {
        log.d("findAll")
        userList.forEach { print(it) }
        return userList
    }

    override fun find(id: UserId): User? {
        log.d("find")
        val user = userList.find { it.id == id }
        user?.let { print(it) }
        return user
    }

    private fun print(user: User) {
        log.d("${user.id.value}/${user.role}/${user.name.value}")
    }
}