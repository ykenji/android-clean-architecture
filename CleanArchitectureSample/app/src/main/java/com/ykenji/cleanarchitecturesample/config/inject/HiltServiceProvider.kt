package com.ykenji.cleanarchitecturesample.config.inject

import android.content.Context
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.domain.adapter.log.Log
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddUseCase
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class HiltServiceProvider @Inject constructor(
    @ApplicationContext val context: Context,
) : ServiceProvider {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface HiltServiceProviderEntryPoint {
        fun getLog(): Log
        fun getUserAddUseCase(): UserAddUseCase
        fun getUserGetListUseCase(): UserGetListUseCase
    }

    private val log by lazy {
        EntryPointAccessors.fromApplication(context, HiltServiceProviderEntryPoint::class.java)
            .getLog()
    }

    private val userAddUseCase by lazy {
        EntryPointAccessors.fromApplication(context, HiltServiceProviderEntryPoint::class.java)
            .getUserAddUseCase()
    }

    private val userGetListUseCase by lazy {
        EntryPointAccessors.fromApplication(context, HiltServiceProviderEntryPoint::class.java)
            .getUserGetListUseCase()
    }

    override fun <T> getService(clazz: Class<T>?): T {
        return when (clazz) {
            UserAddUseCase::class.java -> userAddUseCase
            UserGetListUseCase::class.java -> userGetListUseCase
            else -> throw IllegalArgumentException("Wrong class")
        } as T
    }
}