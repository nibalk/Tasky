package com.nibalk.tasky.core.presentation.utils

import android.net.Uri
import androidx.annotation.DrawableRes


sealed interface UiImage {
    data class DynamicImage(val uri: Uri) : UiImage
    data class ImageResource(@DrawableRes val resId: Int) : UiImage
}


