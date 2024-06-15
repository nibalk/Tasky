package com.nibalk.tasky.core.presentation.themes

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.nibalk.tasky.core.presentation.R


val BackIcon : ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.arrow_left)

val CheckMarkIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.check_mark)

val CrossMarkIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.cross_mark)

val EyeClosedIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.eye_closed)

val EyeOpenedIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.eye_opened)

