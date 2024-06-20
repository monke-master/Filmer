package ru.monke.filmer.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.monke.filmer.R
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.common.SearchField
import ru.monke.filmer.ui.common.ShowItem
import ru.monke.filmer.ui.common.ShowsList
import ru.monke.filmer.ui.mockedList
import ru.monke.filmer.ui.theme.FilmerTheme

@Composable
fun SearchScreen(
) {
    val todayShow = Show(
        title = "Частушки",
        category = "Сектор Газа",
        rating = 99,
        date = 29484884,
        duration = 146,

    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp)
        ) {
            SearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            ShowItem(
                modifier = Modifier.padding(top = 24.dp),
                show = todayShow)
            ShowsList(mockedList, stringResource(id = R.string.recommended_for_you))
        }

    }
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    SearchField(
        modifier = modifier,
        value = text,
        placeholder = { Text(text = stringResource(id = R.string.search_hint)) },
    ) {
        text = it
    }
}



@Preview
@Composable
private fun SearchScreenPreview() {
    FilmerTheme {
        SearchScreen()
    }
}
