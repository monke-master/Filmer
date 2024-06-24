package ru.monke.filmer.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.monke.filmer.di.ApplicationComponent
import ru.monke.filmer.di.DaggerApplicationComponent
import ru.monke.filmer.di.daggerViewModel
import ru.monke.filmer.ui.home.HomeScreen
import ru.monke.filmer.ui.home.HomeViewModel
import ru.monke.filmer.ui.search.SearchScreen
import ru.monke.filmer.ui.theme.FilmerTheme

@Composable
fun ScreenHolder(
    applicationComponent: ApplicationComponent
) {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Home,
        Screen.Search
    )
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, items = items)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { entry ->
                val viewModel = daggerViewModel {
                    applicationComponent.homeViewModelFactory().create(HomeViewModel::class.java)
                }
                HomeScreen(viewModel)
            }
            composable(Screen.Search.route) { SearchScreen() }
        }
    }
}

@Composable
@Preview
fun ScreenHolderPreview() {
    FilmerTheme {
        ScreenHolder(DaggerApplicationComponent.builder().build())
    }
}