package com.ykenji.cleanarchitecturesample.usecase.user

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ykenji.cleanarchitecturesample.clarch.UseCaseBus
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.config.inject.RepositoryModule
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.database.repository.user.RoomUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Singleton

@RunWith(AndroidJUnit4::class)
@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class RoomRepositoryUserUseCaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class TestUserRepositoryModule {

        @Singleton
        @Binds
        abstract fun bindUserRepository(impl: RoomUserRepository): UserRepository
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface UserUseCaseTestEntryPoint {
        fun getServiceProvider(): ServiceProvider
    }

    private val serviceProvider by lazy {
        EntryPointAccessors.fromApplication(
            ApplicationProvider.getApplicationContext(),
            UserUseCaseTestEntryPoint::class.java
        ).getServiceProvider()
    }

    @Inject
    lateinit var bus: UseCaseBus

    @Before
    fun init() {
        hiltRule.inject()
        UserUseCaseTest.init(serviceProvider, bus)
    }

    @Test
    @Throws(Exception::class)
    fun userUseCaseTest_addRemove() {
        UserUseCaseTest.addRemove()
    }

    @Test
    @Throws(Exception::class)
    fun userUseCaseTest_addRemoveSuspend() {
        UserUseCaseTest.addRemoveSuspend()
    }
}