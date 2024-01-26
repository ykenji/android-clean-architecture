package com.ykenji.cleanarchitecturesample.config.inject

import com.ykenji.cleanarchitecturesample.clarch.PresenterBus
import com.ykenji.cleanarchitecturesample.clarch.PresenterBusBuilder
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.clarchimpl.HandlePresenterInvokerFactory
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.Presenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemovePresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PresenterBusModule {

    private val presenterClazzes =
        object :
            HashMap<Class<out OutputData>,
                    Class<out Presenter<out OutputData>>>() {
            init {
                put(UserAddOutputData::class.java, UserAddPresenter::class.java)
                put(UserRemoveOutputData::class.java, UserRemovePresenter::class.java)
                put(UserGetListOutputData::class.java, UserGetListPresenter::class.java)
            }
        }

    @Singleton
    @Provides
    fun providePresenterBus(
        serviceProvider: ServiceProvider,
    ): PresenterBus {
        val builder = PresenterBusBuilder(serviceProvider)
        presenterClazzes.forEach { outputDataClazz, presenterClazz ->
            builder.register(outputDataClazz, presenterClazz)
        }
        return builder.build(HandlePresenterInvokerFactory())
    }
}
