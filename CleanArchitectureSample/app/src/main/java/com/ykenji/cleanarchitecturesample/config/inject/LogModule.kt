package com.ykenji.cleanarchitecturesample.config.inject

import com.ykenji.cleanarchitecturesample.domain.adapter.log.Log
import com.ykenji.cleanarchitecturesample.infrastructure.log.TimberLog
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class LogModule {
    @Singleton
    @Binds
    abstract fun bindLogModule(impl: TimberLog): Log
}
