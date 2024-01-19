package com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE id = :userId")
    fun findById(userId: String): Flow<User?>

    @Insert
    suspend fun insert(user: User)

    @Delete
    suspend fun delete(user: User)
}