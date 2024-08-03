package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nibalk.tasky.core.presentation.R
import com.nibalk.tasky.core.presentation.themes.LocalSpacing
import com.nibalk.tasky.core.presentation.themes.TaskyDarkGray
import com.nibalk.tasky.core.presentation.themes.TaskyGray
import com.nibalk.tasky.core.presentation.themes.TaskyTheme

@Composable
fun TaskyPhotoCarousel(
    maxPhotoCount: Int = 10,
    photos: List<ByteArray?> = emptyList(),
    isEditable: Boolean,
    onPhotoClicked: (ByteArray?) -> Unit,
    onPhotoAddIconClicked: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val arePhotosFull = false
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            itemsIndexed(
                items = photos,
            ) { index, photo ->
                TaskyPhotoThumbnail(
                    photo = photo,
                    onClick = {
                        onPhotoClicked(photo)
                    },
                    footerText = (index + 1).toString(),
                    modifier = Modifier
                        .then(
                            if (index == 0) {
                                Modifier.padding(start = spacing.spaceMedium)
                            } else if (index == photos.lastIndex && index == maxPhotoCount - 1) {
                                Modifier.padding(end = spacing.spaceMedium)
                            } else {
                                Modifier
                            },
                        ),
                )
            }
            if (isEditable && !arePhotosFull) {
                item {
                    TaskyPhotoAddThumbnail(
                        onClick = onPhotoAddIconClicked,
                        modifier = Modifier
                            .padding(end = spacing.spaceMedium),
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskyPhotoThumbnail(
    modifier: Modifier = Modifier,
    photo: ByteArray?,
    footerText: String,
    onClick: () -> Unit,
) {
    TaskyPhotoPlaceholder(
        modifier = modifier,
        onClick = onClick,
        footerText = footerText,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = photo,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.placeholder_photo),
            error = painterResource(id = android.R.drawable.stat_notify_error),
        )
    }
}

@Composable
private fun TaskyPhotoAddThumbnail(
    modifier: Modifier = Modifier,
    iconColor: Color = TaskyGray,
    footerText: String = "",
    onClick: () -> Unit,
) {
    TaskyPhotoPlaceholder(
        onClick = onClick,
        modifier = modifier,
        footerText = footerText
    ) {
        Icon(
            imageVector = Icons.Sharp.Add,
            contentDescription = stringResource(id = R.string.photos_section_title_add),
            tint = iconColor,
        )
    }
}

@Composable
private fun TaskyPhotoPlaceholder(
    modifier: Modifier = Modifier,
    borderColor: Color = TaskyGray,
    borderWidth: Dp = 2.dp,
    thumbnailCorner: Dp = 5.dp,
    thumbnailSize: Dp = 60.dp,
    onClick: () -> Unit,
    footerText: String,
    content: @Composable () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .size(thumbnailSize + borderWidth)
                .clip(RoundedCornerShape(thumbnailCorner))
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(thumbnailCorner),
                )
                .clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
        Text(
            color = TaskyDarkGray,
            text = footerText,
            style = MaterialTheme.typography.displayLarge,
        )
    }
}

// Previews

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyPhotoCarouselPreview() {
    TaskyTheme {
        TaskyPhotoCarousel(
            photos = listOf(null, null, null, null, null),
            isEditable = true,
            onPhotoClicked = {},
            onPhotoAddIconClicked = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyPhotoThumbnailPreview() {
    TaskyTheme {
        TaskyPhotoThumbnail(
            photo = null,
            footerText = "1",
            onClick = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyPhotoAddThumbnailPreview() {
    TaskyTheme {
        TaskyPhotoAddThumbnail(
            onClick = {},
            footerText = ""
        )
    }
}
