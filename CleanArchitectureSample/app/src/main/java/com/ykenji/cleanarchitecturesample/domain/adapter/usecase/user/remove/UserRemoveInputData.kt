package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.remove

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.InputData

class UserRemoveInputData(val userId: String) : InputData<UserRemoveOutputData>