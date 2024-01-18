package com.ykenji.cleanarchitecturesample.infrastructure.datasource.user

import android.content.Context
import com.ykenji.cleanarchitecturesample.domain.adapter.log.Log
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.model.user.User
import com.ykenji.cleanarchitecturesample.domain.model.user.UserId
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class InMemoryUserRepository @Inject constructor(
    @ApplicationContext context: Context,
) : UserRepository {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface InMemoryUserRepositoryEntryPoint {
        fun getLog(): Log
    }

    private val log by lazy {
        EntryPointAccessors.fromApplication(context, InMemoryUserRepositoryEntryPoint::class.java)
            .getLog()
    }

    private val users = ArrayList<User>()

    override fun add(user: User) {
        log.d("add")
        print(user)
        users.add(user)
    }

    override fun remove(user: User) {
        log.d("remove")
        print(user)
        users.remove(user)
    }

    override fun findAll(): List<User> {
        log.d("findAll")
        users.forEach { print(it) }
        return users
    }

    override fun find(id: UserId): User? {
        log.d("find")
        val user = users.find { it.id == id }
        user?.let { print(it) }
        return user
    }

    private fun print(user: User) {
        log.d("${user.id.value}/${user.role}/${user.name.value}")
    }
}