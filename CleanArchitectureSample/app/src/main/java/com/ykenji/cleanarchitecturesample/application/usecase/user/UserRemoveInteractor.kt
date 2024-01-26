package com.ykenji.cleanarchitecturesample.application.usecase.user

import android.content.Context
import com.ykenji.cleanarchitecturesample.application.usecase.user.mapper.UserMapper
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemovePresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class UserRemoveInteractor @Inject constructor(
    @ApplicationContext val context: Context,
) : UserRemoveUseCase {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface UserRemoveInteractorEntryPoint {
        fun getServiceProvider(): ServiceProvider
    }

    private val serviceProvider by lazy {
        EntryPointAccessors.fromApplication(context, UserRemoveInteractorEntryPoint::class.java)
            .getServiceProvider()
    }

    private val userRepository by lazy {
        serviceProvider.getService(UserRepository::class.java)
    }

    private val userRemovePresenter by lazy {
        serviceProvider.getService(UserRemovePresenter::class.java)
    }

    override fun handle(inputData: UserRemoveInputData): UserRemoveOutputData {
        return runBlocking {
            val userId = UserMapper.toUserId(inputData.userId)
            var outputData = UserRemoveOutputData(null)
            userRepository.find(userId).firstOrNull()?.let {
                userRepository.remove(it)
                outputData = UserRemoveOutputData(it.id.value)
                userRemovePresenter.output(outputData)
            }
            outputData
        }
    }

    override suspend fun suspendHandle(inputData: UserRemoveInputData): Flow<UserRemoveOutputData> {
        val userId = UserMapper.toUserId(inputData.userId)
        userRepository.find(userId).firstOrNull()?.let {
            userRepository.remove(it)
            val outputData = UserRemoveOutputData(it.id.value)
            userRemovePresenter.output(outputData)
            return flow {
                emit(outputData)
            }
        }
        return flow {
            emit(UserRemoveOutputData(null))
        }
    }
}