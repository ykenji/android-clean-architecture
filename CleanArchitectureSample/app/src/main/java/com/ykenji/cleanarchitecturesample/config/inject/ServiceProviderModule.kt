package com.ykenji.cleanarchitecturesample.config.inject

import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ServiceProviderModule {

    @Singleton
    @Binds
    abstract fun bindServiceProvider(impl: HiltServiceProvider): ServiceProvider
}
