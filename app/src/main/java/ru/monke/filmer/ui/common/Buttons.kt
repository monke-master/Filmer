package ru.monke.filmer.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.monke.filmer.R
import ru.monke.filmer.ui.theme.SoftBlue


@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit,
    painter: Painter,
    tint: Color = LocalContentColor.current,
    backgroundColor: Color = SoftBlue,
    innerPadding: Dp = 4.dp
) {
    Icon(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(innerPadding)
            .clickable { onClicked() },
        painter = painter,
        contentDescription = null,
        tint = tint
    )
}