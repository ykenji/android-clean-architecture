package com.ykenji.cleanarchitecturesample.domain.model.user

class UserName(value: String) {
    val value: String

    init {
        if (value.length < 3) throw RuntimeException()
        if (value.length > 10) throw RuntimeException()
        this.value = value
    }
}