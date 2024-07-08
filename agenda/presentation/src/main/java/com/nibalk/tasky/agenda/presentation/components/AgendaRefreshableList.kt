package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> AgendaRefreshableList(
    items: List<T>,
    content: @Composable (T) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {

    // val pullToRefreshState = rememberPullToRefreshState()
    val pullToRefreshState = remember {
        object : PullToRefreshState {
            private val anim = Animatable(0f, Float.VectorConverter)

            override val distanceFraction
                get() = anim.value

            override suspend fun animateToThreshold() {
                anim.animateTo(1f, spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessHigh)
                )
            }

            override suspend fun animateToHidden() {
                anim.animateTo(0f)
            }

            override suspend fun snapTo(targetValue: Float) {
                anim.snapTo(targetValue)
            }
        }
    }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) {
            pullToRefreshState.animateToHidden()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        PullToRefreshBox(
            modifier = modifier,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            state = pullToRefreshState
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
                contentPadding = PaddingValues(0.dp),
            ) {
                items(items.size) { index  ->
                    Box(
                        modifier = Modifier
                            .padding(vertical = MaterialTheme.spacing.spaceSmall)
                    ) {
                        content(items[index])
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AgendaRefreshableListPreview() {
    TaskyTheme {
        AgendaRefreshableList(
            items = (1..100).map { "Item $it" },
            content = { itemTitle ->
                Text(
                    text = itemTitle,
                )
            },
            isRefreshing = false,
            onRefresh = {}
        )
    }
}
