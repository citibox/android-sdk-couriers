package com.citibox.courier.sdk.webview.compose

import android.widget.ImageView
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.citibox.courier.sdk.R

@Composable
fun Loading(modifier: Modifier) {
    AndroidView(
        modifier = modifier
            .size(48.dp),
        factory = { context ->
            ImageView(context).apply {
                val drawable = AnimatedVectorDrawableCompat.create(context, R.drawable.ic_loading)
                setImageDrawable(drawable)
                drawable?.start()
            }
        })
}