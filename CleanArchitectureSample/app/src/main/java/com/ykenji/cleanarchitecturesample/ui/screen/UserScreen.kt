package com.ykenji.cleanarchitecturesample.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ykenji.cleanarchitecturesample.R
import com.ykenji.cleanarchitecturesample.presenter.user.model.UiUser
import kotlinx.coroutines.launch

@Composable
fun UsersScreen(viewModel: UserViewModel = viewModel()) {

    val context = LocalContext.current

    val viewState by viewModel.state.collectAsStateWithLifecycle()

    val users by viewModel.users.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.observeUsers()

        launch {
            viewModel.addedUserId.collect { id ->
                Toast.makeText(context, "user $id was added", Toast.LENGTH_SHORT).show()
            }
        }

        launch {
            viewModel.removedUserId.collect { id ->
                Toast.makeText(context, "user $id was removed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    UsersScreenContent(
        users = users,
        userRole = viewState.userRole,
        userName = viewState.userName,
        onUserRoleSelected = viewModel::setUserRole,
        onUserNameChanged = viewModel::setUserName,
        onClickAddButton = viewModel::addUser,
        onUserSelected = viewModel::setSelectedUsers,
        onClickRemoveButton = viewModel::removeUsers,
    )
}

@Composable
private fun UsersScreenContent(
    users: List<UiUser>,
    userRole: String,
    userName: String,
    onUserRoleSelected: (String) -> Unit,
    onUserNameChanged: (String) -> Unit,
    onClickAddButton: () -> Unit,
    onUserSelected: (List<UiUser>) -> Unit,
    onClickRemoveButton: () -> Unit,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())

    ) {
        val options = listOf(
            stringResource(R.string.user_role_member),
            stringResource(R.string.user_role_admin),
        )

        options.forEachIndexed { index, option ->
            Row {
                RadioButton(
                    selected = option == userRole,
                    onClick = { onUserRoleSelected(option) }
                )
                Text(
                    text = options[index]
                )
            }
        }
        Text(
            text = stringResource(R.string.user_name)
        )
        TextField(
            value = userName,
            onValueChange = { onUserNameChanged(it) }
        )

        Button(
            onClick = onClickAddButton,
        ) {
            Text("Add user")
        }

        UserList(
            users,
            onUserSelected
        )

        Button(
            onClick = { onClickRemoveButton() }
        ) {
            Text("Remove user(s)")
        }

    }
}

@Composable
fun UserList(
    userList: List<UiUser>,
    onUserSelected: (List<UiUser>) -> Unit,
) {
    val selectedOptions = remember { mutableStateOf(listOf<UiUser>()) }

    Column {
        userList.forEach { userData ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedOptions.value.contains(userData),
                    onCheckedChange = { selected ->
                        val currentSelected = selectedOptions.value.toMutableList()
                        if (selected) {
                            currentSelected.add(userData)
                        } else {
                            currentSelected.remove(userData)
                        }
                        selectedOptions.value = currentSelected
                        onUserSelected(currentSelected)
                    }
                )
                Text(userData.name)
                Spacer(modifier = Modifier.width(8.dp))
                Text(userData.role)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewUserView() {
    UsersScreenContent(
        users = listOf(
            UiUser("id1", "foo", "admin"),
            UiUser("id2", "bar", "member"),
        ),
        userRole = "member",
        userName = "foo",
        onUserRoleSelected = {},
        onUserNameChanged = {},
        onClickAddButton = {},
        onUserSelected = {},
        onClickRemoveButton = {},
    )
}

