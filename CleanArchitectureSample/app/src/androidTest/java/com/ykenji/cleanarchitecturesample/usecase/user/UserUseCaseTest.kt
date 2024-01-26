package com.ykenji.cleanarchitecturesample.usecase.user

import com.ykenji.cleanarchitecturesample.clarch.UseCaseBus
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddOutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemoveInputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemovePresenter
import com.ykenji.cleanarchitecturesample.domain.model.entity.User
import com.ykenji.cleanarchitecturesample.domain.model.value.UserId
import com.ykenji.cleanarchitecturesample.domain.model.value.UserName
import com.ykenji.cleanarchitecturesample.domain.model.value.UserRole
import com.ykenji.cleanarchitecturesample.presenter.user.mapper.UserMapper
import com.ykenji.cleanarchitecturesample.presenter.user.model.UiUser
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert

object UserUseCaseTest {

    private val user1 = User(UserId("id1"), UserName("user1"), UserRole.ADMIN)
    private val user2 = User(UserId("id2"), UserName("user2"), UserRole.MEMBER)

    private lateinit var serviceProvider: ServiceProvider
    private lateinit var bus: UseCaseBus
    private lateinit var userAddPresenter: UserAddPresenter
    private lateinit var userRemovePresenter: UserRemovePresenter
    private lateinit var userGetListPresenter: UserGetListPresenter

    fun init(serviceProvider: ServiceProvider, bus: UseCaseBus) {
        this.serviceProvider = serviceProvider
        this.bus = bus
        this.userAddPresenter = UserUseCaseTest.serviceProvider
            .getService(UserAddPresenter::class.java)
        this.userRemovePresenter = UserUseCaseTest.serviceProvider
            .getService(UserRemovePresenter::class.java)
        this.userGetListPresenter = UserUseCaseTest.serviceProvider
            .getService(UserGetListPresenter::class.java)
    }

    fun exampleFlowTest() = runTest {
        val values = mutableListOf<Int>()
        val stateFlow = MutableStateFlow(0)
        val job = launch(UnconfinedTestDispatcher()) {
            stateFlow.collect {
                values.add(it)
            }
        }
        stateFlow.value = 1
        stateFlow.value = 2
        stateFlow.value = 3
        job.cancel()
        assertEquals(listOf(0, 1, 2, 3), values)
    }

    fun addRemove() {
        // add
        val out1 = bus.handle(
            UserAddInputData(user1.name.value, user1.role)
        ) as UserAddOutputData
        val out2 = bus.handle(
            UserAddInputData(user2.name.value, user2.role)
        ) as UserAddOutputData
        Assert.assertNotNull(out1.userId)
        Assert.assertNotNull(out2.userId)

        // get list
        val out3 = bus.handle(UserGetListInputData())
        Assert.assertNotNull(
            out3?.users?.find {
                it.name == user1.name.value
            }
        )
        Assert.assertNotNull(
            out3?.users?.find {
                it.name == user2.name.value
            }
        )

        // remove
        out3?.users?.forEach {
            val out4 = bus.handle(UserRemoveInputData(it.id))
            Assert.assertNotNull(out4?.userId)
        }

        // get list
        val out5 = bus.handle(UserGetListInputData())
        Assert.assertNull(
            out5?.users?.find {
                it.name == user1.name.value
            }
        )
        Assert.assertNull(
            out5?.users?.find {
                it.name == user2.name.value
            }
        )
    }

    fun addRemoveSuspend() = runTest {
        // add
        var out1: String? = null
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            out1 = userAddPresenter.outputFlow.first().userId
        }
        bus.suspendHandle(UserAddInputData(user1.name.value, user1.role))
        Assert.assertNotNull(out1)

        var out2: String? = null
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            out2 = userAddPresenter.outputFlow.first().userId
        }
        bus.suspendHandle(UserAddInputData(user2.name.value, user2.role))
        Assert.assertNotNull(out2)

        // get list
        var out3: List<UiUser>? = null
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            userGetListPresenter.outputFlow.collect {
                out3 = it.users.map { UserMapper.toUiUser(it) }
            }
        }
        bus.suspendHandle(UserGetListInputData())?.first()
        Assert.assertNotNull(
            out3?.find {
                it.name == user1.name.value
            }
        )
        Assert.assertNotNull(
            out3?.find {
                it.name == user2.name.value
            }
        )

        // remove
        val out4 = mutableListOf<String?>()
        out3?.forEach {
            backgroundScope.launch(UnconfinedTestDispatcher()) {
                out4.add(userRemovePresenter.outputFlow.first().userId)
            }
            bus.suspendHandle(UserRemoveInputData(it.id))
        }
        Assert.assertEquals(out3?.size, out4.size)

        // get list
        var out5: List<UiUser>? = null
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            userGetListPresenter.outputFlow.collect {
                out5 = it.users.map { UserMapper.toUiUser(it) }
            }
        }
        bus.suspendHandle(UserGetListInputData())?.first()
        Assert.assertNull(
            out5?.find {
                it.name == user1.name.value
            }
        )
        Assert.assertNull(
            out5?.find {
                it.name == user2.name.value
            }
        )
    }
}