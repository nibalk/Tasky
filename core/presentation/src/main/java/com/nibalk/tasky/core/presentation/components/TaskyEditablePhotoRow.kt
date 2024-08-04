package com.nibalk.tasky.core.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.nibalk.tasky.core.presentation.R
import com.nibalk.tasky.core.presentation.themes.TaskyDarkGray
import com.nibalk.tasky.core.presentation.themes.TaskyGray
import com.nibalk.tasky.core.presentation.themes.TaskyLightBlue
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing
import com.nibalk.tasky.core.presentation.utils.drawableToByteArray

@Composable
fun TaskyEditablePhotoRow(
    maxPhotoCount: Int = 10,
    photos: List<ByteArray?> = emptyList(),
    onPhotoViewed: (ByteArray) -> Unit,
    onPhotoAdded: (Uri) -> Unit,
    isEditable: Boolean,
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { imageUri ->
                onPhotoAdded(imageUri)
            }
        }
    )

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
                color = TaskyDarkGray,
                text = stringResource(id = R.string.photos_main_title),
                style = MaterialTheme.typography.displayLarge
            )
            if (photos.isEmpty()) {
                TaskyEmptyPhotoSection(isEditable) {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            } else {
                TaskyPhotoCarousel(
                    photos = photos,
                    isEditable = isEditable,
                    maxPhotoCount = maxPhotoCount,
                    onPhotoAddIconClicked = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    onPhotoClicked = { photoBytes ->
                        if (photoBytes != null) {
                            onPhotoViewed(photoBytes)
                        }
                    }
                )
            }
        }
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
        Column {
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
                Icon(
                    imageVector = Icons.Sharp.Add,
                    tint = TaskyGray,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.spaceSmall))
                Text(
                    color = TaskyGray,
                    style = MaterialTheme.typography.displayMedium,
                    text = if (isEditable) {
                        stringResource(id = R.string.photos_section_title_add)
                    } else {
                        stringResource(id = R.string.photos_section_title_no)
                    },
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceLarge))
        }
    }
}

// Preview functions

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyEditablePhotoRowEmptyPreview() {
    val drawable = ContextCompat.getDrawable(
        LocalContext.current,
        android.R.drawable.spinner_background
    )
    val photo = drawable?.drawableToByteArray()

    TaskyTheme {
        TaskyEditablePhotoRow(
            photos = listOf(photo, photo, photo, photo, photo),
            onPhotoAdded = {},
            onPhotoViewed = {},
            isEditable = true,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyEditablePhotoRowPreview() {
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
