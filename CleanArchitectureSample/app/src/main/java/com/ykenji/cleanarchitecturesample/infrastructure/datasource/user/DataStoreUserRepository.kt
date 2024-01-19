package com.ykenji.cleanarchitecturesample.infrastructure.datasource.user

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.ykenji.cleanarchitecturesample.config.inject.ApplicationScope
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.model.user.User
import com.ykenji.cleanarchitecturesample.domain.model.user.UserId
import com.ykenji.cleanarchitecturesample.domain.model.user.UserName
import com.ykenji.cleanarchitecturesample.domain.model.user.UserRole
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.user.DataStoreUserRepository.PreferencesKeys.KEY_USERS
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.usersDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import javax.inject.Inject

class DataStoreUserRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @ApplicationScope coroutineScope: CoroutineScope,
) : UserRepository, CoroutineScope by coroutineScope {

    private object PreferencesKeys {
        val KEY_USERS = stringSetPreferencesKey("users")
    }

    override val users: Flow<List<User>>
        get() = context.usersDataStore.data
            .catch { e ->
                // dataStore.data throws an IOException when an error is encountered when reading data
                when (e) {
                    is IOException -> {
                        emit(emptyPreferences())
                    }

                    else -> throw e
                }
            }.map { pref ->
                (pref[KEY_USERS] ?: emptySet()).map {
                    stringToUser(it)
                }.toList()
            }

    override fun add(user: User) {
        launch(Dispatchers.IO) {
            context.usersDataStore.edit { pref ->
                (pref[KEY_USERS] ?: emptySet()).toMutableSet().let {
                    it.add(userToString(user))
                    pref[KEY_USERS] = it
                }
            }
        }
    }

    override fun remove(user: User) {
        launch(Dispatchers.IO) {
            context.usersDataStore.edit { pref ->
                val sources = pref[KEY_USERS]?.toMutableSet() ?: return@edit
                sources.remove(userToString(user))
                if (sources.isEmpty()) {
                    pref.remove(KEY_USERS)
                } else {
                    pref[KEY_USERS] = sources
                }
            }
        }
    }

    override fun findAll(): List<User> {
        return runBlocking {
            users.first()
        }
    }

    override fun find(id: UserId): User? {
        return runBlocking {
            users.first().find { it.id.value == id.value }
        }
    }

    private fun userToString(user: User): String {
        return "${user.id.value}#${user.name.value}#${user.role}"
    }

    private fun stringToUser(string: String): User {
        return string.split("#").let {
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
}