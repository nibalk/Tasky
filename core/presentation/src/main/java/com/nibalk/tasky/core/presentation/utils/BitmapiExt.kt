package com.nibalk.tasky.core.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

fun Bitmap.toByteArrayMax1MB(
    photoMaxSize: Int = 1024 * 1024 // 1MB
): ByteArray {
    val stream = ByteArrayOutputStream()
    var quality = 100
    do {
        stream.reset()
        this.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        if (stream.size() > photoMaxSize) {
            quality -= 10 // Decrease quality by 10%
        }
    } while (stream.size() > photoMaxSize && quality > 0)
    return stream.toByteArray()
}

suspend fun Uri.getCompressedByteArray(
    context: Context,
    uploadTThreshold: Int = 1024 * 1024 // 1MB
): ByteArray? = withContext(Dispatchers.IO) {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true // Decode bounds without loading the entire image
    }
    val imageSize = options.outWidth * options.outHeight // Approximate image size

    options.inJustDecodeBounds = false
    val bitmap = BitmapFactory.decodeStream(
        context.contentResolver.openInputStream(this@getCompressedByteArray),
        null, options
    )

    if (imageSize > uploadTThreshold) {
        bitmap?.toCompressedByteArray()
    } else {
        bitmap?.toByteArray()
    }
}

private suspend fun Bitmap.toCompressedByteArray(
    quality: Int = 50
): ByteArray = withContext(Dispatchers.IO) {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, quality, stream)
    stream.toByteArray()
}

private suspend fun Bitmap.toByteArray(
    quality: Int = 100
): ByteArray = withContext(Dispatchers.IO) {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, quality, stream) // Use PNG for lossless conversion
    stream.toByteArray()
}
