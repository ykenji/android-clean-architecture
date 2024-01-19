package com.ykenji.cleanarchitecturesample.config.inject

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.usersDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class UsersDataStore


@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @UsersDataStore
    @Singleton
    @Provides
    fun provideUsersDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> {
        return context.usersDataStore
    }
}