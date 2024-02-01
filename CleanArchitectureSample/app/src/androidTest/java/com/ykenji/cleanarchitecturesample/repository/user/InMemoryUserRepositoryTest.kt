package com.ykenji.cleanarchitecturesample.repository.user

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ykenji.cleanarchitecturesample.config.inject.RepositoryModule
import com.ykenji.cleanarchitecturesample.domain.adapter.repository.user.UserRepository
import com.ykenji.cleanarchitecturesample.infrastructure.datasource.inmemory.repository.user.InMemoryUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
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
class InMemoryUserRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class TestUserRepositoryModule {

        @Singleton
        @Binds
        abstract fun bindUserRepository(impl: InMemoryUserRepository): UserRepository
    }

    @Inject
    lateinit var userRepository: InMemoryUserRepository

    @Before
    fun init() {
        hiltRule.inject()
        UserRepositoryTest.init(userRepository)
    }

    @Test
    @Throws(Exception::class)
    fun userRepositoryTest_addRemove() {
        UserRepositoryTest.addRemove()
    }
}