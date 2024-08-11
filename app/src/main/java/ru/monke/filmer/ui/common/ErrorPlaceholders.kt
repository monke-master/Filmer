package ru.monke.filmer.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.monke.filmer.R
import ru.monke.filmer.ui.theme.FilmerTheme
import ru.monke.filmer.ui.theme.White

@Composable
fun ErrorPlaceholder(
    errorMessage: String? = null,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.error),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = White
        )
        errorMessage?.let {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = errorMessage,
                textAlign = TextAlign.Center,
                color = White
            )
        }
        Button(
            onClick = onRetry,
            modifier = Modifier.padding(top = 8.dp),
        ) {
            Text(text = stringResource(id = R.string.try_again),)
        }
    }
}

@Composable
@Preview
private fun ErrorPlaceholderPreview() {
    FilmerTheme {
        Surface {
            ErrorPlaceholder(
                errorMessage = "В предшествующие исторические эпохи мы находим почти повсюду\n" +
                        "полное расчленение общества на различные сословия, – целую лестницу\n" +
                        "различных общественных положений. "
            ) {

            }
        }
    }
}