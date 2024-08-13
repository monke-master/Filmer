package ru.monke.filmer.ui.keyboard

import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun registerKeyboardListener(
    onKeyboardStatusChanged: (Boolean) -> Unit
) {
    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver
    DisposableEffect(viewTreeObserver) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat
                .getRootWindowInsets(view)?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            onKeyboardStatusChanged(isKeyboardOpen)
        }

        viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}
