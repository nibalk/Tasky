package com.nibalk.tasky.core.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream

fun Uri.getCompressedByteArray(
    context: Context,
    uploadTThreshold: Int = 1024 * 1024 // 1MB
): ByteArray?  {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true // Decode bounds without loading the entire image
    }
    val imageSize = options.outWidth * options.outHeight // Approximate image size

    options.inJustDecodeBounds = false
    val bitmap = BitmapFactory.decodeStream(
        context.contentResolver.openInputStream(this), null, options
    )

    if (imageSize > uploadTThreshold) {
        return bitmap?.toCompressedByteArray()
    } else {
        return bitmap?.toByteArray()
    }
}

private fun Bitmap.toCompressedByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 50, stream)
    return stream.toByteArray()
}

private fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, stream) // Use PNG for lossless conversion
    return stream.toByteArray()
}
