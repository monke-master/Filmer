package ru.monke.filmer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.monke.filmer.ui.theme.FilmerTheme
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val client = OkHttpClient()

        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://streaming-availability.p.rapidapi.com/shows/search/filters")
                .get()
                .addHeader("x-rapidapi-key", "4ccc906dbbmshc6cb6a39df81420p1d2e4bjsn7a5c6777899c")
                .addHeader("x-rapidapi-host", "streaming-availability.p.rapidapi.com")
                .build()

            val response = client.newCall(request).execute()

            Log.d("MainActivity", "onCreate: " + response.code())
        }

        setContent {
            FilmerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FilmerTheme {
        Greeting("Android")
    }
}