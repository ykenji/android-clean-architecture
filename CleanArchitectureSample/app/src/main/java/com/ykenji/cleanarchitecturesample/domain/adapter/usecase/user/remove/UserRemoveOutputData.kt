package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData

class UserRemoveOutputData : OutputData {
    var userId: String? = null
        private set

    constructor(userId: String?) {
        this.userId = userId
    }
}