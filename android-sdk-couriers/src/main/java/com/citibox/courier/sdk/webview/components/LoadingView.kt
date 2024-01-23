package com.citibox.courier.sdk.webview.components

import android.content.Context
import android.util.AttributeSet
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.citibox.courier.sdk.R

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        val drawable = AnimatedVectorDrawableCompat.create(context, R.drawable.ic_loading)
        setImageDrawable(drawable)
        drawable?.start()
    }
}