package com.ykenji.cleanarchitecturesample.controller.user

import android.content.Context
import com.ykenji.cleanarchitecturesample.clarch.UseCaseBus
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveInputData
import com.ykenji.cleanarchitecturesample.domain.model.value.UserRole
import com.ykenji.cleanarchitecturesample.presenter.user.mapper.UserMapper
import com.ykenji.cleanarchitecturesample.presenter.user.model.VmUser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserController @Inject constructor(
    @ApplicationContext val context: Context,
) {
    @Inject
    lateinit var bus: UseCaseBus

    suspend fun createUser(name: String, roleId: String) {
        val role: UserRole = convertRole(roleId)
        val inputData = UserAddInputData(name, role)
        bus.suspendHandle(inputData)
    }

    suspend fun removeUsers(userIds: List<String>) {
        userIds.forEach {
            val inputData = UserRemoveInputData(it)
            bus.suspendHandle(inputData)
        }
    }

    suspend fun getUsers(): Flow<List<VmUser>>? {
        val inputData = UserGetListInputData()
        return bus.suspendHandle(inputData)?.map { it ->
            it.users.map { UserMapper.toVmUser(it) }
        }
    }

    private fun convertRole(roleId: String): UserRole {
        return when (roleId) {
            "admin" -> UserRole.ADMIN
            "member" -> UserRole.MEMBER
            else -> throw RuntimeException()
        }
    }
}