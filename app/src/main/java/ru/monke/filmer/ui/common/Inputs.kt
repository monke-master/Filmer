package ru.monke.filmer.ui.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ru.monke.filmer.R
import ru.monke.filmer.ui.theme.Grey
import ru.monke.filmer.ui.theme.SoftBlue
import ru.monke.filmer.ui.theme.White

@Composable
fun InputFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedContainerColor = SoftBlue,
        unfocusedContainerColor = SoftBlue,
        disabledContainerColor = SoftBlue,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        focusedTextColor = White,
        disabledTextColor = White,
        focusedPlaceholderColor = Grey,
        unfocusedPlaceholderColor = Grey,
        disabledPlaceholderColor = Grey
    )
}

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChanged: (String) -> Unit,
    enabled: Boolean = true
) {
    TextField(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = InputFieldColors(),
        value = value,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = Grey
            )
        },
        trailingIcon = trailingIcon,
        maxLines = 1,
        placeholder = placeholder,
        onValueChange = onValueChanged,
        enabled = enabled
    )
}

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChanged: (TextFieldValue) -> Unit,
    enabled: Boolean = true
) {
    TextField(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = InputFieldColors(),
        value = value,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = Grey
            )
        },
        trailingIcon = trailingIcon,
        maxLines = 1,
        placeholder = placeholder,
        onValueChange = onValueChanged,
        enabled = enabled
    )
}
