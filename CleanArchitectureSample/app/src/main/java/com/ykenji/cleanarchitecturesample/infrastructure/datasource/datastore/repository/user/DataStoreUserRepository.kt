package com.ykenji.cleanarchitecturesample.infrastructure.datasource.datastore.repository.user

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.ykenji.cleanarchitecturesample.config.inject.ApplicationScope
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.model.entity.User
import com.ykenji.cleanarchitecturesample.domain.model.value.UserId
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.datastore.mapper.UserMapper
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.datastore.repository.user.DataStoreUserRepository.PreferencesKeys.KEY_USERS
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.datastore.usersDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreUserRepository @Inject constructor(
    @ApplicationContext val context: Context,
    @ApplicationScope coroutineScope: CoroutineScope,
) : UserRepository, CoroutineScope by coroutineScope {

    private object PreferencesKeys {
        val KEY_USERS = stringSetPreferencesKey("users")
    }

    override suspend fun add(user: User) {
        context.usersDataStore.edit { pref ->
            (pref[KEY_USERS] ?: emptySet()).toMutableSet().let {
                it.add(UserMapper.toString(user))
                pref[KEY_USERS] = it
            }
        }
    }

    override suspend fun remove(user: User) {
        context.usersDataStore.edit { pref ->
            val sources = pref[KEY_USERS]?.toMutableSet() ?: return@edit
            sources.removeIf {
                UserMapper.toUser(it).id.value == user.id.value
            }
            if (sources.isEmpty()) {
                pref.remove(KEY_USERS)
            } else {
                pref[KEY_USERS] = sources
            }
        }
    }

    override fun findAll() = context.usersDataStore.data
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
                UserMapper.toUser(it)
            }.toList()
        }

    override fun find(id: UserId) = context.usersDataStore.data
        .catch { e ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            when (e) {
                is IOException -> {
                    emit(emptyPreferences())
                }

                else -> throw e
            }
        }.map { pref ->
            pref[KEY_USERS]?.map {
                UserMapper.toUser(it)
            }?.find { it.id.value == id.value }
        }
}