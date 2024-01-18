package com.ykenji.cleanarchitecturesample.application.usecase.user

import android.content.Context
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddUseCase
import com.ykenji.cleanarchitecturesample.domain.model.user.User
import com.ykenji.cleanarchitecturesample.domain.model.user.UserId
import com.ykenji.cleanarchitecturesample.domain.model.user.UserName
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.UUID
import javax.inject.Inject

class UserAddInteractor @Inject constructor(
    @ApplicationContext val context: Context,
) : UserAddUseCase {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface UserAddInteractorEntryPoint {
        fun getUserRepository(): UserRepository
        fun getUserAddPresenter(): UserAddPresenter
    }

    private val userRepository by lazy {
        EntryPointAccessors.fromApplication(context, UserAddInteractorEntryPoint::class.java)
            .getUserRepository()
    }

    private val userAddPresenter by lazy {
        EntryPointAccessors.fromApplication(context, UserAddInteractorEntryPoint::class.java)
            .getUserAddPresenter()
    }

    override fun handle(inputData: UserAddInputData): UserAddOutputData {
        val uuid = UUID.randomUUID().toString()
        val user = User(
            UserId(uuid),
            UserName(inputData.userName),
            inputData.role
        )
        userRepository.add(user)
        val outputData = UserAddOutputData(uuid)
        userAddPresenter.output(outputData)
        return outputData
    }
}