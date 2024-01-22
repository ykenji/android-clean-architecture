package com.ykenji.cleanarchitecturesample.ui.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.domain.adapter.log.Log
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.common.UserData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListPresenter
import com.ykenji.cleanarchitecturesample.presentation.controller.UserController
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    @ApplicationContext context: Context,
) : ViewModel() {

    @Inject
    lateinit var userController: UserController

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface UserViewModelEntryPoint {
        fun getServiceProvider(): ServiceProvider
    }

    private val serviceProvider by lazy {
        EntryPointAccessors.fromApplication(context, UserViewModelEntryPoint::class.java)
            .getServiceProvider()
    }

    private val log by lazy {
        serviceProvider.getService(Log::class.java)
    }

    private val userAddPresenter by lazy {
        serviceProvider.getService(UserAddPresenter::class.java)
    }

    private val userGetListPresenter by lazy {
        serviceProvider.getService(UserGetListPresenter::class.java)
    }

    // Holds our view state which the UI collects via [state]
    val state: StateFlow<UserViewState>
        get() = _state
    private val _state = MutableStateFlow(UserViewState())

    private val userRole: StateFlow<String>
        get() = _userRole
    private val _userRole = MutableStateFlow("member")

    private val userName: StateFlow<String>
        get() = _userName
    private val _userName = MutableStateFlow("")

    val addedUserId: StateFlow<String?>
        get() = _addedUserId
    private val _addedUserId: MutableStateFlow<String?> = MutableStateFlow("")

    val userList: StateFlow<List<UserData>>
        get() = _userList
    private val _userList: MutableStateFlow<List<UserData>> = MutableStateFlow(emptyList())

    init {
        viewModelScope.launch {
            // Combine our flows and collect them into the view state StateFlow
            combine(userRole, userName) { userRole, userName ->
                UserViewState(
                    userRole = userRole,
                    userName = userName
                )
            }.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _state.value = it
            }
        }

        viewModelScope.launch {
            // Combine our flows and collect them into the view state StateFlow
            userAddPresenter.userId.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                _addedUserId.value = it
            }
        }

        viewModelScope.launch {
            // Combine our flows and collect them into the view state StateFlow
            userGetListPresenter.users.catch { throwable ->
                // TODO: emit a UI error here. For now we'll just rethrow
                throw throwable
            }.collect {
                log.d("collect!!")
                _userList.value = it
            }
        }
    }

    fun setUserRole(userRole: String) {
        _userRole.value = userRole
    }

    fun setUserName(userName: String) {
        _userName.value = userName
    }

    fun addUser() {
        viewModelScope.launch {
            log.d("addUser")
            userController.createUser(userName.value, userRole.value)
        }
    }

    fun getUserList() {
        viewModelScope.launch {
            log.d("getUserList")
            userController.getUsers()
        }
    }
}

data class UserViewState(
    val userRole: String = "member",
    val userName: String = "",
)