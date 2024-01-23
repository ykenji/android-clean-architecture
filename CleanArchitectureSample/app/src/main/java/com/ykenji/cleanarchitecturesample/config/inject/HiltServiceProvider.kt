package com.ykenji.cleanarchitecturesample.config.inject

import android.content.Context
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.domain.adapter.log.Log
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddUseCase
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListUseCase
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemovePresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveUseCase
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
        // use case
        fun getUserAddUseCase(): UserAddUseCase
        fun getUserRemoveUseCase(): UserRemoveUseCase
        fun getUserGetListUseCase(): UserGetListUseCase

        // presenter
        fun getUserAddPresenter(): UserAddPresenter
        fun getUserRemovePresenter(): UserRemovePresenter
        fun getUserGetListPresenter(): UserGetListPresenter

        // infrastructure
        fun getLog(): Log
        fun getUserRepository(): UserRepository
    }

    override fun <T> getService(clazz: Class<T>?): T {
        return EntryPointAccessors
            .fromApplication(context, HiltServiceProviderEntryPoint::class.java)
            .run {
                when (clazz) {
                    // use case
                    UserAddUseCase::class.java -> getUserAddUseCase()
                    UserRemoveUseCase::class.java -> getUserRemoveUseCase()
                    UserGetListUseCase::class.java -> getUserGetListUseCase()
                    // presenter
                    UserAddPresenter::class.java -> getUserAddPresenter()
                    UserRemovePresenter::class.java -> getUserRemovePresenter()
                    UserGetListPresenter::class.java -> getUserGetListPresenter()
                    // infrastructure
                    Log::class.java -> getLog()
                    UserRepository::class.java -> getUserRepository()
                    else -> throw IllegalArgumentException("Wrong class")
                } as T
            }
    }
}