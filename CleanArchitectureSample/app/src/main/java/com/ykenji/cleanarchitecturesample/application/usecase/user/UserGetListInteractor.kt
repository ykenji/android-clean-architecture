package com.ykenji.cleanarchitecturesample.application.usecase.user

import android.content.Context
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.common.UserData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListUseCase
import com.ykenji.cleanarchitecturesample.domain.model.user.User
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.coroutineScope
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

    private val userGetListPresenter by lazy {
        serviceProvider.getService(UserGetListPresenter::class.java)
    }

    override fun handle(inputData: UserGetListInputData): UserGetListOutputData {
        val userData = userRepository.findAll().map { x: User ->
            UserData(
                x.id.value,
                x.name.value,
                x.role
            )
        }
        val outputData = UserGetListOutputData(userData)
        userGetListPresenter.output(outputData)
        return outputData
    }

    override suspend fun suspendHandle(inputData: UserGetListInputData) {
        coroutineScope {
            userRepository.users.collect {
                val userData = it.map { x: User ->
                    UserData(
                        x.id.value,
                        x.name.value,
                        x.role
                    )
                }
                val outputData = UserGetListOutputData(userData)
                userGetListPresenter.output(outputData)
            }
        }
    }
}