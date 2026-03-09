package com.example.bookshelf.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.max
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.BookShelfApplication
import com.example.bookshelf.R
import com.example.bookshelf.ui.screens.BookShelfViewModel
import com.example.bookshelf.ui.screens.HomeScreen
import com.example.bookshelf.ui.screens.viewModelFactories

enum class BookShelfScreen {
    Home,
    Detailed
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App (
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BookShelfScreen.valueOf(
        backStackEntry?.destination?.route ?: BookShelfScreen.Home.name
    )
    Scaffold(
        topBar = {
            BookShelfTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
    ) {
        val localLayout = LocalLayoutDirection.current
        val safePadding = WindowInsets.safeDrawing.asPaddingValues()
        val bottom = max(
            safePadding.calculateBottomPadding(),
            it.calculateBottomPadding()
        )
        val context = LocalContext.current
        val bookShelfApp = (context.applicationContext as  BookShelfApplication)
        val bookShelfViewModel: BookShelfViewModel = viewModel(
            factory = viewModelFactories.BookShelfViewModelFactory(
                bookShelfApp.container.bookShelfRepository
            )
        )
        val uiBookState by bookShelfViewModel.bookShelf.collectAsStateWithLifecycle()

        Surface(
            modifier = Modifier
                .padding(
                    start = safePadding
                        .calculateStartPadding(localLayout),
                    end = safePadding
                        .calculateEndPadding(localLayout),
                    top = it.calculateTopPadding(),
                    bottom = bottom
                )
        ) {
            NavHost(
                navController = navController,
                startDestination = BookShelfScreen.Home.name,
                modifier = Modifier
            ) {
                composable(route = BookShelfScreen.Home.name) {
                    HomeScreen(
                        uiState = uiBookState,
                        retryAction = bookShelfViewModel::getBooks,
                        selectAction = { },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookShelfTopAppBar(
    currentScreen: BookShelfScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(text = currentScreen.name)
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        modifier = modifier
    )
}