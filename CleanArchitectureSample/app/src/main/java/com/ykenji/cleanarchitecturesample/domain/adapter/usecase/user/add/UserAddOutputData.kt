package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.OutputData

class UserAddOutputData : OutputData {
    var userId: String? = null
        private set

    constructor(userId: String) {
        this.userId = userId
    }
}