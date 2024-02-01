package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.getlist

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData
import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.common.UcUser

class UserGetListOutputData : OutputData {
    var users: List<UcUser>
        private set

    constructor(users: List<UcUser>) {
        this.users = users
    }
}