package com.nibalk.tasky.core.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.net.URL

suspend fun URL.getCompressedByteArray(): ByteArray = withContext(Dispatchers.IO) {
    val url = this@getCompressedByteArray
    url.readBytes()
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

    ensureActive() // Check for cancellation after decoding
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
