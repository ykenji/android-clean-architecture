package com.ykenji.cleanarchitecturesample.application.usecase.user

import android.content.Context
import com.ykenji.cleanarchitecturesample.application.usecase.user.mapper.UserMapper
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class UserGetListInteractor @Inject constructor(
    @ApplicationContext val context: Context,
) : UserGetListUseCase {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface UserGetListInteractorEntryPoint {
        fun getServiceProvider(): ServiceProvider
    }

    private val serviceProvider by lazy {
        EntryPointAccessors.fromApplication(context, UserGetListInteractorEntryPoint::class.java)
            .getServiceProvider()
    }

    private val userRepository by lazy {
        serviceProvider.getService(UserRepository::class.java)
    }

    private val userGetPresetnter by lazy {
        serviceProvider.getService(UserGetListPresenter::class.java)
    }

    override fun handle(inputData: UserGetListInputData): UserGetListOutputData {
        return runBlocking {
            userRepository.findAll().first().map {
                UserMapper.toUserData(it)
            }.let {
                val outputData = UserGetListOutputData(it)
                userGetPresetnter.output(outputData)
                outputData
            }
        }
    }

    override suspend fun suspendHandle(inputData: UserGetListInputData) {
        coroutineScope {
            userRepository.findAll().collect {
                val userData = it.map { UserMapper.toUserData(it) }
                val outputData = UserGetListOutputData(userData)
                userGetPresetnter.output(outputData)
            }
        }
    }
}