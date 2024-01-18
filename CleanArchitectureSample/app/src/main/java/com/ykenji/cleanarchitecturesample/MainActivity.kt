package com.ykenji.cleanarchitecturesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ykenji.cleanarchitecturesample.domain.adapter.log.Log
import com.ykenji.cleanarchitecturesample.ui.screen.UsersScreen
import com.ykenji.cleanarchitecturesample.ui.theme.CleanArchitectureSampleTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var log: Log

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App {
                UsersScreen()
            }
        }
    }
}

@Composable
fun App(content: @Composable () -> Unit) {
    CleanArchitectureSampleTheme(
        darkTheme = isSystemInDarkTheme()
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    App {
        UsersScreen()
    }
}