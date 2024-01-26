package com.ykenji.cleanarchitecturesample.ui.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykenji.cleanarchitecturesample.clarch.inject.ServiceProvider
import com.ykenji.cleanarchitecturesample.controller.UserController
import com.ykenji.cleanarchitecturesample.domain.adapter.log.Log
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add.UserAddPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist.UserGetListPresenter
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove.UserRemovePresenter
import com.ykenji.cleanarchitecturesample.presenter.mapper.UserMapper
import com.ykenji.cleanarchitecturesample.presenter.model.UiUser
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

    private val userRemovePresenter by lazy {
        serviceProvider.getService(UserRemovePresenter::class.java)
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

    val addedUserId = userAddPresenter.outputFlow.map {
        it.userId
    }

    val removedUserId = userRemovePresenter.outputFlow.map {
        it.userId
    }

    val users: StateFlow<List<UiUser>> = userGetListPresenter.outputFlow.map {
        it.users.map {
            UserMapper.toUiUser(it)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    private val selectedUsers: StateFlow<List<UiUser>>
        get() = _selectedUsers
    private val _selectedUsers: MutableStateFlow<List<UiUser>> = MutableStateFlow(emptyList())

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
    }

    fun setUserRole(userRole: String) {
        _userRole.value = userRole
    }

    fun setUserName(userName: String) {
        _userName.value = userName
    }

    fun setSelectedUsers(users: List<UiUser>) {
        _selectedUsers.value = users
    }

    fun addUser() {
        viewModelScope.launch {
            log.d("addUser")
            userController.createUser(userName.value, userRole.value)
        }
    }

    fun removeUsers() {
        viewModelScope.launch {
            log.d("removeUsers")
            userController.removeUsers(selectedUsers.value.map { it.id })
        }
    }

    fun observeUsers() {
        viewModelScope.launch {
            userController.getUsers()?.collect {
                log.d("user list was updated")
            }
        }
    }
}

data class UserViewState(
    val userRole: String = "member",
    val userName: String = "",
)