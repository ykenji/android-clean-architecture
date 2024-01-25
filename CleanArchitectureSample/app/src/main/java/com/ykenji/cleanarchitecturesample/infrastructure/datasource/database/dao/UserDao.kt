package com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.entity.DbUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM dbuser")
    fun getAll(): Flow<List<DbUser>>

    @Query("SELECT * FROM dbuser WHERE id = :userId")
    fun findById(userId: String): Flow<DbUser?>

    @Insert
    suspend fun insert(user: DbUser)

    @Delete
    suspend fun delete(user: DbUser)
}