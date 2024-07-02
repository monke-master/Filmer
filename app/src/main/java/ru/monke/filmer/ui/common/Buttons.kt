package ru.monke.filmer.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.monke.filmer.R
import ru.monke.filmer.ui.theme.SoftBlue


@Composable
fun BackButton(
    onClicked: () -> Unit
) {
    Icon(
        modifier = Modifier
            .background(
                color = SoftBlue,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(4.dp)
            .clickable { onClicked() },
        painter = painterResource(id = R.drawable.ic_back),
        contentDescription = null
    )
}