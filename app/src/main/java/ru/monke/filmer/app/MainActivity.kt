package ru.monke.filmer.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.monke.filmer.ui.navigation.ScreenHolder
import ru.monke.filmer.ui.theme.FilmerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmerTheme {
                ScreenHolder(application.appComponent())
            }
        }
    }
}