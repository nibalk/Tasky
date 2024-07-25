package com.nibalk.tasky.core.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

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

private suspend fun Bitmap.toCompressedByteArray(): ByteArray = withContext(Dispatchers.IO) {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 50, stream)
    stream.toByteArray()
}

private suspend fun Bitmap.toByteArray(): ByteArray = withContext(Dispatchers.IO) {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, stream) // Use PNG for lossless conversion
    stream.toByteArray()
}
