package ru.monke.filmer.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.monke.filmer.R
import ru.monke.filmer.di.ApplicationComponent
import ru.monke.filmer.di.DaggerApplicationComponent
import ru.monke.filmer.di.daggerViewModel
import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.home.HomeScreen
import ru.monke.filmer.ui.home.HomeViewModel
import ru.monke.filmer.ui.search.SearchScreen
import ru.monke.filmer.ui.search.SearchViewModel
import ru.monke.filmer.ui.search.result.SearchResultScreen
import ru.monke.filmer.ui.search.result.SearchResultViewModel
import ru.monke.filmer.ui.show.ShowScreen
import ru.monke.filmer.ui.show.ShowViewModel
import ru.monke.filmer.ui.showlist.ShowsListScreen
import ru.monke.filmer.ui.showlist.ShowsListViewModel
import ru.monke.filmer.ui.theme.FilmerTheme

lateinit var appComponent: ApplicationComponent

@Composable
fun ScreenHolder(
    applicationComponent: ApplicationComponent
) {
    val navController = rememberNavController()
    appComponent = applicationComponent
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Search
    )
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, items = items)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            navigation(
                route = NavigationItem.Home.route,
                startDestination = "${NavigationItem.Home.route}/start"
            ) {
                composable("${NavigationItem.Home.route}/start") { entry ->
                    val viewModel = daggerViewModel {
                        applicationComponent.homeViewModelFactory().create(HomeViewModel::class.java)
                    }
                    HomeScreen(
                        viewModel = viewModel,
                        onShowItemClicked = { navController.navigate("${NavigationItem.Home.route}/show/${it.id}") },
                        toShowsListNav = {
                            navController.navigate("showsList/bestShows")
                        }
                    )
                }
                showScreen(NavigationItem.Home.route, navController)
                composable(
                    route = "showsList/bestShows",
                ) { navBackStackEntry ->
                    val viewModel = daggerViewModel {
                        applicationComponent.topShowsViewModelFactory().create(ShowsListViewModel::class.java)
                    }
                    ShowsListScreen(
                        showsListViewModel = viewModel,
                        title = stringResource(id = R.string.most_popular),
                        onShowItemClicked = { navController.navigate("${NavigationItem.Home.route}/show/${it.id}") },
                        onBackBtnClicked = { navController.popBackStack() }
                    )
                }
            }
            navigation(
                route = NavigationItem.Search.route,
                startDestination = "${NavigationItem.Search.route}/start"
            ) {
                composable("${NavigationItem.Search.route}/start") {
                    val viewModel = daggerViewModel {
                        applicationComponent.searchViewModelFactory().create(SearchViewModel::class.java)
                    }
                    SearchScreen(
                        searchViewModel = viewModel,
                        onShowItemClicked = { navController.navigate("${NavigationItem.Search.route}/show/${it.id}") },
                        toShowsListNav = { genre ->
                            navController.navigate("showsList/${genre.id}&${genre.name}")
                        },
                        onSearchFieldClicked = {
                            navController.navigate("search/searchResult")
                        }
                    )
                }
                showScreen(NavigationItem.Search.route, navController)
                showsListScreen(navController)
                composable("search/searchResult") {
                    val viewModel = daggerViewModel {
                        applicationComponent.searchResultViewModel().create(SearchResultViewModel::class.java)
                    }

                    SearchResultScreen(
                        viewModel = viewModel,
                        onCancelBtnClicked = { navController.popBackStack() },
                        onShowClicked = { navController.navigate("${NavigationItem.Search.route}/show/${it.id}") },
                    )
                }
            }
        }
    }
}

private fun NavGraphBuilder.showScreen(
    route: String,
    navController: NavController
) {
    composable(
        route = "$route/show/{showId}",
        arguments = listOf(navArgument("showId") { type = NavType.StringType })
    ) { backStackEntry ->
        val showId = backStackEntry.arguments?.getString(Params.SHOW_ID_PARAM)!!
        val viewModel = daggerViewModel {
            appComponent.showViewModelFactory().create(ShowViewModel::class.java, showId)
        }
        ShowScreen(
            viewModel = viewModel,
            onBackButtonClicked = {
                navController.popBackStack()
            }
        )
    }
}

private fun NavGraphBuilder.showsListScreen(
    navController: NavController
) {
    composable(
        route = "showsList/{genreId}&{genreName}",
        arguments = listOf(
            navArgument("genreId") { type = NavType.StringType },
            navArgument("genreName") { type = NavType.StringType },
        )
    ) { navBackStack ->
        val genreId = navBackStack.arguments?.getString("genreId")!!
        val genreName = navBackStack.arguments?.getString("genreName")!!
        val genre = Genre(genreId, genreName)
        val viewModel = daggerViewModel {
            appComponent.recommendedShowViewModelFactory().create(ShowsListViewModel::class.java, genre)
        }
        ShowsListScreen(
            showsListViewModel = viewModel,
            title = stringResource(id = R.string.recommended_for_you),
            onShowItemClicked = { navController.navigate("show/${it.id}") },
            onBackBtnClicked = { navController.popBackStack() }
        )
    }
}


@Composable
@Preview
fun ScreenHolderPreview() {
    FilmerTheme {
        ScreenHolder(DaggerApplicationComponent.builder().build())
    }
}