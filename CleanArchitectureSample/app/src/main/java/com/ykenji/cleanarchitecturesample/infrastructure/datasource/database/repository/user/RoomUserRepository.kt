package com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.repository.user

import android.content.Context
import com.ykenji.cleanarchitecturesample.config.inject.ApplicationScope
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.model.entity.User
import com.ykenji.cleanarchitecturesample.domain.model.value.UserId
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.AppDatabase
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.mapper.UserMapper
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
        userDao.insert(UserMapper.toDbUser(user))

    override suspend fun remove(user: User) =
        userDao.delete(UserMapper.toDbUser(user))

    override fun findAll() = userDao.getAll().map { it ->
        it.map {
            UserMapper.toUser(it)
        }
    }

    override fun find(id: UserId) = userDao.findById(id.value).map {
        it?.let {
            UserMapper.toUser(it)
        }
    }
}