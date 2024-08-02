package com.nibalk.tasky.core.presentation.components

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.core.presentation.R
import com.nibalk.tasky.core.presentation.themes.TaskyBlack
import com.nibalk.tasky.core.presentation.themes.TaskyGray
import com.nibalk.tasky.core.presentation.themes.TaskyLightBlue
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing
import com.nibalk.tasky.core.presentation.utils.toByteArrayMax1MB

@Composable
fun TaskyEditablePhotoRow(
    maxPhotoCount: Int = 5,
    photos: List<ByteArray?> = emptyList(),
    onPhotoViewed: (ByteArray) -> Unit,
    onPhotoAdded: (Uri) -> Unit,
    isEditable: Boolean,
) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
            uri?.let { imageUri ->
                onPhotoAdded(imageUri)
//                context.contentResolver.openInputStream(imageUri)
//                    ?.use { inputStream ->
//                        BitmapFactory.decodeStream(inputStream)
//                    }?.let { bitmap ->
//                        onPhotoAdded(bitmap.toByteArrayMax1MB())
//                    }
            }
        }
    )

    val imageModifier = Modifier
        .alpha(0.8f)
        .size(60.dp)
        .clip(RoundedCornerShape(MaterialTheme.spacing.spaceExtraSmall))
        .border(border = BorderStroke(2.dp, TaskyGray))

    Column(
        modifier = Modifier
            .background(color = TaskyLightBlue),
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                color = TaskyBlack,
                text = stringResource(id = R.string.photos_main_title),
                style = MaterialTheme.typography.displayLarge
            )
            if (photos.isEmpty()) {
                TaskyEmptyPhotoSection(isEditable) {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                    photoPickerLauncher
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    val chunkedPhotosList = photos.chunked(maxPhotoCount)

                    Column(
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.spaceSmall)
                    ) {
                        for (groupOfPhotos in chunkedPhotosList) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround,
                            ) {
                                for (photo in groupOfPhotos) {
                                    TaskyPhotoThumbnail(
                                        imageModifier, photo, onPhotoViewed
                                    )
                                }
                                if (groupOfPhotos.size < maxPhotoCount && isEditable) {
                                    Image(
                                        painter = painterResource(id = android.R.drawable.ic_input_add),
                                        modifier = imageModifier
                                            .clickable {
                                                photoPickerLauncher.launch(
                                                    PickVisualMediaRequest(
                                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                                    )
                                                )
                                            },
                                        contentScale = ContentScale.Fit,
                                        contentDescription = null,
                                    )
                                    repeat(4 - groupOfPhotos.size) {
                                        Text(text = "", modifier = Modifier.size(60.dp))
                                    }
                                } else {
                                    repeat(maxPhotoCount - groupOfPhotos.size) {
                                        Text(text = "", modifier = Modifier.size(60.dp))
                                    }
                                }
                            }
                            if (photos.size % maxPhotoCount == 0 && isEditable) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Image(
                                        painter = painterResource(id = android.R.drawable.ic_input_add),
                                        contentDescription = null,
                                        modifier = imageModifier
                                            .clickable {
                                                photoPickerLauncher.launch(
                                                    PickVisualMediaRequest(
                                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                                    )
                                                )
                                            },
                                        contentScale = ContentScale.Fit
                                    )
                                    repeat(maxPhotoCount - 1) {
                                        Text(text = "", modifier = Modifier.size(60.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))
    }
}

@Composable
private fun TaskyEmptyPhotoSection(
    isEditable: Boolean,
    onPhotoAddIconClicked: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .then(
                    if (isEditable) {
                        Modifier.clickable {
                            onPhotoAddIconClicked()
                        }
                    } else Modifier,
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_input_add),
                colorFilter = ColorFilter.tint(TaskyGray),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.spaceSmall))
            Text(
                color = TaskyGray,
                style = MaterialTheme.typography.displayMedium,
                text = stringResource(id = R.string.photos_add_section_title),
            )
        }
    }
}

@Composable
private fun TaskyPhotoThumbnail(
    modifier: Modifier = Modifier,
    photo: ByteArray?,
    onPhotoViewed: (ByteArray) -> Unit,
) {
    Image(
        painter = BitmapPainter(
            BitmapFactory.decodeByteArray(
                photo, 0, photo?.size ?: 0
            ).asImageBitmap()
        ),
        modifier = modifier
            .clickable {
                if (photo != null) {
                    onPhotoViewed(photo)
                }
            },
        contentScale = ContentScale.FillBounds,
        contentDescription = null
    )
}

// Preview functions

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyEditablePhotoRowEmptyPreview() {
    TaskyTheme {
        TaskyEditablePhotoRow(
            onPhotoAdded = {},
            onPhotoViewed = {},
            isEditable = true,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyEmptyPhotoSectionPreview() {
    TaskyTheme {
        TaskyEmptyPhotoSection(
            onPhotoAddIconClicked = {},
            isEditable = true,
        )
    }
}

//@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
//@Composable
//private fun TaskyPhotoThumbnailPreview() {
//    val context = LocalContext.current
//    val inputStream = context.resources.openRawResource(R.raw.sample_image)
//
//    TaskyTheme {
//        TaskyPhotoThumbnail(
//            onPhotoViewed = {},
//            photo = inputStream.readBytes(),
//        )
//    }
//}

