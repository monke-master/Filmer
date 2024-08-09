package ru.monke.filmer.ui.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.orbitmvi.orbit.compose.collectAsState
import ru.monke.filmer.R
import ru.monke.filmer.domain.ALL_GENRE
import ru.monke.filmer.domain.Genre
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.action
import ru.monke.filmer.ui.comedy
import ru.monke.filmer.ui.common.LoadingPlaceholder
import ru.monke.filmer.ui.common.SearchField
import ru.monke.filmer.ui.common.ShowsList
import ru.monke.filmer.ui.common.UpdatableShowItem
import ru.monke.filmer.ui.common.UpdatableShowsList
import ru.monke.filmer.ui.common.repeat
import ru.monke.filmer.ui.getMocked
import ru.monke.filmer.ui.theme.FilmerTheme
import kotlin.math.log

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    onShowItemClicked: (Show) -> Unit = {},
    toShowsListNav: (Genre) -> Unit,
    onSearchFieldClicked: () -> Unit
) {
    val state by searchViewModel.collectAsState()
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        if (state.isLoading) {
            LoadingPlaceholder(text = stringResource(id = R.string.loading_shows))
        } else if (state.error != null) {
            Text(text = "Лошара")
        } else if (state.isSuccess){
            SearchScreenContent(
                genres = state.genres,
                todayShowState = state.todayShowState!!,
                recommendedShowsState = state.recommendedShowsState!!,
                onShowItemClicked = onShowItemClicked,
                onGenreSelected = searchViewModel::fetchDataByGenre,
                onShowLoad = searchViewModel::loadNextShows,
                toShowsListNav = toShowsListNav,
                selectedGenre = state.selectedGenre,
                onSearchFieldClicked = onSearchFieldClicked
            )
        }
    }
}

@Composable
private fun SearchScreenContent(
    genres: List<Genre>,
    todayShowState: TodayShowState,
    recommendedShowsState: RecommendedShowsState,
    onShowItemClicked: (Show) -> Unit,
    onGenreSelected: (Genre) -> Unit,
    onShowLoad: () -> Unit,
    toShowsListNav: (Genre) -> Unit,
    selectedGenre: Genre,
    onSearchFieldClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .verticalScroll(rememberScrollState())
    ) {
        SearchTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .clickable {
                    Log.d("ALAKKA", "SearchScreenContent: ")
                    onSearchFieldClicked()
                },
        )
        GenresChipGroup(
            genres = genres,
            modifier = Modifier.padding(top = 16.dp),
            onGenreSelected = onGenreSelected
        )
        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = R.string.today),
            style = MaterialTheme.typography.headlineLarge,
        )
        UpdatableShowItem(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            show = todayShowState.todayShow,
            onItemClicked = onShowItemClicked,
            isUpdating = todayShowState.isLoading)
        UpdatableShowsList(
            shows = recommendedShowsState.shows,
            title = stringResource(id = R.string.recommended_for_you),
            onItemClicked = onShowItemClicked,
            onShowLoad = onShowLoad,
            isUpdating = recommendedShowsState.isLoading,
            onSeeAllBtnClicked = {
                toShowsListNav(selectedGenre)
            })
    }
}

@Composable
fun GenresChipGroup(
    genres: List<Genre>,
    modifier: Modifier = Modifier,
    onGenreSelected: (Genre) -> Unit
) {
    var selectedCategory by remember {
        mutableStateOf(genres[0])
    }
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres) { genre ->
            if (genre == selectedCategory) {
                SelectedCategoryChip(title = genre.name)
            } else {
                CategoryChip(
                    modifier = Modifier
                        .clickable {
                            onGenreSelected(genre)
                            selectedCategory = genre
                        },
                    title = genre.name
                )
            }
        }
    }
}

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        text = title,
        modifier = modifier.padding(horizontal = 18.dp, vertical = 8.dp)
    )
}

@Composable
fun SelectedCategoryChip(
    modifier: Modifier = Modifier,
    title: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
) {
    var text by remember { mutableStateOf("") }
    SearchField(
        modifier = modifier,
        value = text,
        placeholder = { Text(text = stringResource(id = R.string.search_hint)) },
        onValueChanged = {
            text = it
        },
        enabled = false
    )

}

@Preview
@Composable
private fun SearchScreenPreview() {
    FilmerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            SearchScreenContent(
                genres = listOf(action, comedy),
                TodayShowState(getMocked(LocalContext.current.resources)),
                RecommendedShowsState(listOf(getMocked(LocalContext.current.resources)).repeat(4)),
                {},
                {},
                onShowLoad = {},
                selectedGenre = ALL_GENRE,
                toShowsListNav = {},
                onSearchFieldClicked = {}
            )
        }
    }
}
