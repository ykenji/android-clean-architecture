package com.ykenji.cleanarchitecturesample.config.inject;

import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.repository.user.RoomUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindUserRepository(impl: RoomUserRepository): UserRepository
}
