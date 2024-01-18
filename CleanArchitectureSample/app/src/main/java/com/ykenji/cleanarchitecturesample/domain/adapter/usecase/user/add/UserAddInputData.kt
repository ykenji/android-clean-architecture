package com.ykenji.cleanarchitecturesample.domain.adapter.usecase.user.add

import com.ykenji.cleanarchitecturesample.domain.adapter.usecase.core.InputData
import com.ykenji.cleanarchitecturesample.domain.model.user.UserRole

class UserAddInputData(val userName: String, val role: UserRole) : InputData<UserAddOutputData>