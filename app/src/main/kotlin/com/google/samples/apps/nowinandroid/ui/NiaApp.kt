/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.google.samples.apps.nowinandroid.R
import com.google.samples.apps.nowinandroid.R.string
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaBackground
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaGradientBackground
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaNavigationSuiteScaffold
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaTopAppBar
import com.google.samples.apps.nowinandroid.core.designsystem.icon.NiaIcons
import com.google.samples.apps.nowinandroid.core.designsystem.theme.GradientColors
import com.google.samples.apps.nowinandroid.core.designsystem.theme.LocalGradientColors
import com.google.samples.apps.nowinandroid.core.navigation.Navigator
import com.google.samples.apps.nowinandroid.core.navigation.toEntries
import com.google.samples.apps.nowinandroid.core.ui.util.LocalSnackbarHostState
import com.google.samples.apps.nowinandroid.feature.bookmarks.impl.navigation.bookmarksEntry
import com.google.samples.apps.nowinandroid.feature.foryou.api.navigation.ForYouNavKey
import com.google.samples.apps.nowinandroid.feature.foryou.impl.navigation.forYouEntry
import com.google.samples.apps.nowinandroid.feature.interests.impl.navigation.interestsEntry
import com.google.samples.apps.nowinandroid.feature.news.impl.navigation.newsDetailEntry
import com.google.samples.apps.nowinandroid.feature.news.impl.navigation.newsEntry
import com.google.samples.apps.nowinandroid.feature.search.api.navigation.SearchNavKey
import com.google.samples.apps.nowinandroid.feature.search.impl.navigation.searchEntry
import com.google.samples.apps.nowinandroid.feature.settings.impl.SettingsDialog
import com.google.samples.apps.nowinandroid.feature.topic.impl.navigation.topicEntry
import com.google.samples.apps.nowinandroid.navigation.TOP_LEVEL_NAV_ITEMS
import com.google.samples.apps.nowinandroid.feature.settings.impl.R as settingsR

@Composable
fun NiaApp(
    appState: NiaAppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val shouldShowGradientBackground = appState.navigationState.currentTopLevelKey == ForYouNavKey
    var showSettingsDialog by rememberSaveable { mutableStateOf(false) }

    NiaBackground(modifier = modifier) {
        NiaGradientBackground(
            gradientColors = if (shouldShowGradientBackground) {
                LocalGradientColors.current
            } else {
                GradientColors()
            },
        ) {
            val snackbarHostState = remember { SnackbarHostState() }

            val isOffline by appState.isOffline.collectAsStateWithLifecycle()

            // If user is not connected to the internet show a snack bar to inform them.
            val notConnectedMessage = stringResource(string.not_connected)
            LaunchedEffect(isOffline) {
                if (isOffline) {
                    snackbarHostState.showSnackbar(
                        message = notConnectedMessage,
                        duration = Indefinite,
                    )
                }
            }
            CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
                NiaApp(
                    appState = appState,

                    // TODO: Settings should be a dialog screen
                    showSettingsDialog = showSettingsDialog,
                    onSettingsDismissed = { showSettingsDialog = false },
                    onTopAppBarActionClick = { showSettingsDialog = true },
                    windowAdaptiveInfo = windowAdaptiveInfo,
                )
            }
        }
    }
}

@Composable
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeUiApi::class,
    ExperimentalMaterial3AdaptiveApi::class,
)
internal fun NiaApp(
    appState: NiaAppState,
    showSettingsDialog: Boolean,
    onSettingsDismissed: () -> Unit,
    onTopAppBarActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    if (showSettingsDialog) {
        SettingsDialog(
            onDismiss = { onSettingsDismissed() },
        )
    }

    val navigator = remember { Navigator(appState.navigationState) }
    val currentKey = appState.navigationState.currentKey
    val isTopLevel = currentKey in appState.navigationState.topLevelKeys

    val entryProvider = remember(navigator) {
        entryProvider {
            forYouEntry(navigator)
            bookmarksEntry(navigator)
            interestsEntry(navigator)
            topicEntry(navigator)
            searchEntry(navigator)
            newsEntry(navigator)
            newsDetailEntry(navigator)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isTopLevel,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            TopLevelScaffoldContainer(
                appState = appState,
                navigator = navigator,
                windowAdaptiveInfo = windowAdaptiveInfo,
                onTopAppBarActionClick = onTopAppBarActionClick,
            ) {
                NavDisplay(
                    entries = appState.navigationState.toEntries(entryProvider),
                    onBack = { navigator.goBack() },
                )
            }
        }

        AnimatedVisibility(
            visible = !isTopLevel,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            if (!isTopLevel) {
                NavDisplay(
                    entries = appState.navigationState.toEntries(entryProvider),
                    onBack = { navigator.goBack() },
                )
            }
        }
    }
}

@Composable
private fun TopLevelScaffoldContainer(
    appState: NiaAppState,
    navigator: Navigator,
    windowAdaptiveInfo: WindowAdaptiveInfo,
    onTopAppBarActionClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val unreadNavKeys by appState.topLevelNavKeysWithUnreadResources
        .collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current

    NiaNavigationSuiteScaffold(
        navigationSuiteItems = {
            TOP_LEVEL_NAV_ITEMS.forEach { (navKey, navItem) ->
                val hasUnread = unreadNavKeys.contains(navKey)
                val selected = navKey == appState.navigationState.currentTopLevelKey
                item(
                    selected = selected,
                    onClick = { navigator.navigate(navKey) },
                    icon = { Icon(navItem.unselectedIcon, contentDescription = null) },
                    selectedIcon = { Icon(navItem.selectedIcon, contentDescription = null) },
                    label = { Text(stringResource(navItem.iconTextId)) },
                    modifier = Modifier.then(if (hasUnread) Modifier.notificationDot() else Modifier),
                )
            }
        },
        windowAdaptiveInfo = windowAdaptiveInfo,
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                val destination = TOP_LEVEL_NAV_ITEMS[appState.navigationState.currentTopLevelKey]
                if (destination != null) {
                    NiaTopAppBar(
                        titleRes = destination.titleTextId,
                        navigationIcon = NiaIcons.Search,
                        navigationIconContentDescription = stringResource(
                            id = settingsR.string.feature_settings_impl_top_app_bar_navigation_icon_description,
                        ),
                        actionIcon = NiaIcons.Settings,
                        actionIconContentDescription = stringResource(
                            id = settingsR.string.feature_settings_impl_top_app_bar_action_icon_description,
                        ),
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                        ),
                        onActionClick = { onTopAppBarActionClick() },
                        onNavigationClick = { navigator.navigate(SearchNavKey) },
                    )
                }

                Box(modifier = Modifier.weight(1f)) {
                    content()
                }
            }
        }
    }
}

private fun Modifier.notificationDot(): Modifier =
    composed {
        val tertiaryColor = MaterialTheme.colorScheme.tertiary
        drawWithContent {
            drawContent()
            drawCircle(
                tertiaryColor,
                radius = 5.dp.toPx(),
                // This is based on the dimensions of the NavigationBar's "indicator pill";
                // however, its parameters are private, so we must depend on them implicitly
                // (NavigationBarTokens.ActiveIndicatorWidth = 64.dp)
                center = center + Offset(
                    64.dp.toPx() * .45f,
                    32.dp.toPx() * -.45f - 6.dp.toPx(),
                ),
            )
        }
    }
