package com.ykenji.cleanarchitecturesample.config.inject

import com.ykenji.cleanarchitecturesample.clarch.UseCaseBus
import com.ykenji.cleanarchitecturesample.clarch.UseCaseBusBuilder
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.clarchimpl.HandleUseCaseInvokerFactory
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.InputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.UseCase
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddUseCase
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListUseCase
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseBusModule {

    private val useCaseClazzes =
        object :
            HashMap<Class<out InputData<out OutputData>>,
                    Class<out UseCase<out InputData<out OutputData>, out OutputData>>>() {
            init {
                put(UserAddInputData::class.java, UserAddUseCase::class.java)
                put(UserRemoveInputData::class.java, UserRemoveUseCase::class.java)
                put(UserGetListInputData::class.java, UserGetListUseCase::class.java)
            }
        }

    @Singleton
    @Provides
    fun provideUseCaseBus(
        serviceProvider: ServiceProvider,
    ): UseCaseBus {
        val builder = UseCaseBusBuilder(serviceProvider)
        useCaseClazzes.forEach { inputDataClazz, useCaseClazz ->
            builder.register(inputDataClazz, useCaseClazz)
        }
        return builder.build(HandleUseCaseInvokerFactory())
    }
}
