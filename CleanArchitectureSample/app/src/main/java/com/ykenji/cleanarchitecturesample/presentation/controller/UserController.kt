package com.ykenji.cleanarchitecturesample.presentation.controller

import android.content.Context
import com.ykenji.cleanarchitecturesample.clarch.UseCaseBus
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListInputData
import com.ykenji.cleanarchitecturesample.domain.model.user.UserRole
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class UserController @Inject constructor(
    @ApplicationContext val context: Context,
) {
    @Inject
    lateinit var bus: UseCaseBus

    fun createUser(name: String, roleId: String) {
        val role: UserRole = convertRole(roleId)
        val inputData = UserAddInputData(name, role)
        bus.handle(inputData)
    }

    fun getUsers() {
        val inputData = UserGetListInputData()
        bus.handle(inputData)
    }

    private fun convertRole(roleId: String): UserRole {
        return when (roleId) {
            "admin" -> UserRole.ADMIN
            "member" -> UserRole.MEMBER
            else -> throw RuntimeException()
        }
    }
}