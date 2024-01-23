package com.ykenji.cleanarchitecturesample.config.inject

import com.ykenji.cleanarchitecturesample.adapter.presenter.FlowUserAddPresenter
import com.ykenji.cleanarchitecturesample.adapter.presenter.FlowUserGetListPresenter
import com.ykenji.cleanarchitecturesample.adapter.presenter.FlowUserRemovePresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemovePresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class PresenterModule {

    @Singleton
    @Binds
    abstract fun bindUserAddPresenter(impl: FlowUserAddPresenter): UserAddPresenter

    @Singleton
    @Binds
    abstract fun bindUserRemovePresenter(impl: FlowUserRemovePresenter): UserRemovePresenter

    @Singleton
    @Binds
    abstract fun bindUserGetListPresenter(impl: FlowUserGetListPresenter): UserGetListPresenter
}