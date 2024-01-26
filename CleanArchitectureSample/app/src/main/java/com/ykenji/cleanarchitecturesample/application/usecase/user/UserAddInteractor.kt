package com.ykenji.cleanarchitecturesample.application.usecase.user

import android.content.Context
import com.ykenji.cleanarchitecturesample.application.usecase.user.mapper.UserMapper
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.util.UUID
import javax.inject.Inject

class UserAddInteractor @Inject constructor(
    @ApplicationContext val context: Context,
) : UserAddUseCase {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface UserAddInteractorEntryPoint {
        fun getServiceProvider(): ServiceProvider
    }

    private val serviceProvider by lazy {
        EntryPointAccessors.fromApplication(context, UserAddInteractorEntryPoint::class.java)
            .getServiceProvider()
    }

    private val userRepository by lazy {
        serviceProvider.getService(UserRepository::class.java)
    }

    private val userAddPresenter by lazy {
        serviceProvider.getService(UserAddPresenter::class.java)
    }

    override fun handle(inputData: UserAddInputData): UserAddOutputData {
        val uuid = UUID.randomUUID().toString()
        val user = UserMapper.toUser(uuid, inputData.userName, inputData.role)
        val outputData = UserAddOutputData(uuid)
        return runBlocking {
            userRepository.add(user)
            userAddPresenter.output(outputData)
            outputData
        }
    }

    override suspend fun suspendHandle(inputData: UserAddInputData): Flow<UserAddOutputData> {
        val uuid = UUID.randomUUID().toString()
        val user = UserMapper.toUser(uuid, inputData.userName, inputData.role)
        userRepository.add(user)
        val outputData = UserAddOutputData(uuid)
        userAddPresenter.output(outputData)
        return flow {
            emit(outputData)
        }
    }
}