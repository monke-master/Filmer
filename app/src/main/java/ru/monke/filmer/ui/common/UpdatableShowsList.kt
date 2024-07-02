package ru.monke.filmer.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.monke.filmer.R
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.theme.BlueAccent
import ru.monke.filmer.ui.theme.FilmerTheme

@Composable
fun UpdatableShowsList(
    modifier: Modifier = Modifier,
    shows: List<Show>,
    title: String,
    onItemClicked: (Show) -> Unit = {},
    onShowLoad: () -> Unit = {},
    isUpdating: Boolean,
    onSeeAllBtnClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(top = 24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = stringResource(id = R.string.see_all),
            style = MaterialTheme.typography.headlineMedium,
            color = BlueAccent,
            modifier = Modifier.clickable { onSeeAllBtnClicked() }
        )
    }

    LazyRow(
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(shows.size) { index ->
            if (index >= shows.size - 1) {
                onShowLoad()
            }
            val show = shows[index]
            SmallShowItem(
                show = show,
                modifier = Modifier.clickable { onItemClicked(show) }
            )
        }
        if (isUpdating) {
            item {
                LoadingPlaceholder(
                    modifier = Modifier.fillParentMaxWidth(),
                    itemsCount = shows.size
                )
            }
        }
    }
}

@Composable
private fun LoadingPlaceholder(
    modifier: Modifier = Modifier,
    itemsCount: Int
) {
    if (itemsCount == 0) {
        Box(
            modifier = modifier.height(231.dp),
            contentAlignment = Alignment.Center
        ) {
            LoadingIndicator(modifier = Modifier.size(48.dp))
        }
    } else {
        LoadingIndicator(modifier = Modifier.size(48.dp))
    }
}

@Composable
@Preview
private fun EmptyLoadingListPreview() {
    FilmerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            UpdatableShowsList(
                shows = emptyList(),
                title = "This is Preview",
                isUpdating = true,
                onSeeAllBtnClicked = {})
        }
    }
}