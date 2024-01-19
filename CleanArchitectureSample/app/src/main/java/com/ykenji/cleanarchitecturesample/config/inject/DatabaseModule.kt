package com.ykenji.cleanarchitecturesample.config.inject

import android.content.Context
import androidx.room.Room
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
}