package com.ykenji.cleanarchitecturesample.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ykenji.cleanarchitecturesample.R
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.common.UserData

@Composable
fun UsersScreen(viewModel: UserViewModel = viewModel()) {

    val context = LocalContext.current

    val viewState by viewModel.state.collectAsStateWithLifecycle()

    val userList by viewModel.userList.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getUserList()

        viewModel.addedUserId.collect { id ->
            Toast.makeText(context, "user $id was added", Toast.LENGTH_LONG).show()
        }
    }

    UsersScreenContent(
        userList = userList,
        userRole = viewState.userRole,
        userName = viewState.userName,
        onUserRoleSelected = viewModel::setUserRole,
        onUserNameChanged = viewModel::setUserName,
        onClickAddButton = viewModel::addUser,
        onClickRemoveButton = {},
    )
}

@Composable
private fun UsersScreenContent(
    userList: List<UserData>,
    userRole: String,
    userName: String,
    onUserRoleSelected: (String) -> Unit,
    onUserNameChanged: (String) -> Unit,
    onClickAddButton: () -> Unit,
    onClickRemoveButton: () -> Unit,
) {
    Column {
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

        LazyColumn {
            items(userList) { user ->
                Text(user.name)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewUserView() {
    UsersScreenContent(
        userList = emptyList(),
        userRole = "member",
        userName = "foo",
        onUserRoleSelected = {},
        onUserNameChanged = {},
        onClickAddButton = {},
        onClickRemoveButton = {},
    )
}

