package com.ykenji.cleanarchitecturesample.infrastructure.datasource.user

import android.content.Context
import com.ykenji.cleanarchitecturesample.config.inject.ApplicationScope
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.model.user.User
import com.ykenji.cleanarchitecturesample.domain.model.user.UserId
import com.ykenji.cleanarchitecturesample.domain.model.user.UserName
import com.ykenji.cleanarchitecturesample.domain.model.user.UserRole
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.AppDatabase
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.model.DbUser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomUserRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @ApplicationScope coroutineScope: CoroutineScope,
    db: AppDatabase,
) : UserRepository, CoroutineScope by coroutineScope {

    private val userDao = db.userDao()

    override suspend fun add(user: User) =
        userDao.insert(userToDbUser(user))

    override suspend fun remove(user: User) =
        userDao.delete(userToDbUser(user))

    override fun findAll() = userDao.getAll().map { it ->
        it.map {
            dbUserToUser(it)
        }
    }

    override fun find(id: UserId) = userDao.findById(id.value).map {
        it?.let {
            dbUserToUser(it)
        }
    }

    private fun userToDbUser(user: User) =
        DbUser(user.id.value, user.name.value, user.role.name)

    private fun dbUserToUser(dbUser: DbUser) =
        User(
            UserId(dbUser.id), UserName(dbUser.name),
            when (dbUser.role) {
                UserRole.ADMIN.name -> UserRole.ADMIN
                else -> UserRole.MEMBER
            }
        )
}