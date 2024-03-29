package com.ykenji.cleanarchitecturesample.infrastructure.datasource.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.usersDataStore by preferencesDataStore(
    name = "users_preferences"
)