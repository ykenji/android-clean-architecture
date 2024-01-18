package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.common.UserData

class UserGetListOutputData : OutputData {
    var users: List<UserData>
        private set

    constructor(users: List<UserData>) {
        this.users = users
    }
}