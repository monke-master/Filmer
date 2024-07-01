package ru.monke.filmer.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.monke.filmer.R
import ru.monke.filmer.domain.Show
import ru.monke.filmer.ui.common.ShowItem
import ru.monke.filmer.ui.theme.FilmerTheme
import ru.monke.filmer.ui.theme.Grey
import ru.monke.filmer.ui.theme.White

@Composable
fun SearchResultList(
    modifier: Modifier = Modifier,
    results: List<Show>
) {
    LazyColumn {
        items(results) { show ->
            ShowItem(show = show, onItemClicked =  {})
        }
    }
}

@Composable()
@Preview(showBackground = true)
fun SearchResultPreview() {
    FilmerTheme {
        Surface(modifier = Modifier.fillMaxSize()){
            SearchResultList(results = listOf(
//                Show(title = "Rambo", category = "Action", rating = 69),
//                Show(title = "Red Flame", category = "Action", rating = 56),
//                Show(title = "Terminator 2", category = "Action", rating = 89),
            ))
        }
    }
}

@Composable
fun NotFoundPlaceholder(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_no_results),
            contentDescription = null,
            tint = Color.Unspecified)
        Text(
            modifier = Modifier.padding(start = 90.dp, end = 90.dp, top = 16.dp),
            text = stringResource(id = R.string.not_found),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            color = White,
            textAlign = TextAlign.Center)
        Text(
            modifier = Modifier.padding(horizontal = 90.dp, vertical = 8.dp),
            text = stringResource(id = R.string.not_found_hint),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = Grey,
            textAlign = TextAlign.Center)
    }
}

@Composable
@Preview(showBackground = true)
fun NotFoundPlaceholderPreview() {
    FilmerTheme {
        Surface {
            NotFoundPlaceholder(modifier = Modifier.fillMaxSize())
        }

    }
}