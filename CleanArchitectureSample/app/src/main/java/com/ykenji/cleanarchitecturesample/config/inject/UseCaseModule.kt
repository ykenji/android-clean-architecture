package com.ykenji.cleanarchitecturesample.config.inject;

import com.ykenji.cleanarchitecturesample.application.usecase.user.UserAddInteractor
import com.ykenji.cleanarchitecturesample.application.usecase.user.UserGetListInteractor
import com.ykenji.cleanarchitecturesample.application.usecase.user.UserRemoveInteractor
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddUseCase
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListUseCase
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {
    @Singleton
    @Binds
    abstract fun bindUserAddUseCase(impl: UserAddInteractor): UserAddUseCase

    @Singleton
    @Binds
    abstract fun bindUserRemoveUseCase(impl: UserRemoveInteractor): UserRemoveUseCase

    @Singleton
    @Binds
    abstract fun bindUserGetListUseCase(impl: UserGetListInteractor): UserGetListUseCase
}
