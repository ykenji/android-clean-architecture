package com.ykenji.cleanarchitecturesample.infrastructure.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.dao.UserDao
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}