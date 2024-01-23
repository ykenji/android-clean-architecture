package com.ykenji.cleanarchitecturesample.application.usecase.user

import android.content.Context
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemovePresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveUseCase
import com.ykenji.cleanarchitecturesample.domain.model.user.UserId
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
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
            val userId = UserId(inputData.userId)
            var outputData = UserRemoveOutputData(null)
            userRepository.find(userId).filterNotNull().collect {
                userRepository.remove(it)
                outputData = UserRemoveOutputData(it.id.value)
                userRemovePresenter.output(outputData)
            }
            outputData
        }
    }

    override suspend fun suspendHandle(inputData: UserRemoveInputData) {
        coroutineScope {
            val userId = UserId(inputData.userId)
            userRepository.find(userId).firstOrNull()?.let {
                userRepository.remove(it)
                val outputData = UserRemoveOutputData(it.id.value)
                userRemovePresenter.output(outputData)
            }
        }
    }
}