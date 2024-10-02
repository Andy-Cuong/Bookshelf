package com.example.bookshelf.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookshelf.R
import com.example.bookshelf.ui.screens.BookshelfUiState
import com.example.bookshelf.ui.screens.BookshelfViewModel
import com.example.bookshelf.ui.screens.HomeScreen
import com.example.bookshelf.ui.screens.ResultScreen

@Composable
fun BookshelfApp(
    bookshelfViewModel: BookshelfViewModel = viewModel(factory = BookshelfViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val uiState = bookshelfViewModel.bookshelfUiState
    val currentScreenName = if (uiState is BookshelfUiState.Success) {
        stringResource(
            BookshelfScreen.ResultScreen.title,
            uiState.booksQueryResult.totalItems,
            bookshelfViewModel.searchQuery
        )
    } else {
        stringResource(BookshelfScreen.HomeScreen.title)
    }

    Scaffold(
        topBar = {
            BookshelfTopAppBar(
                currentScreenName = currentScreenName,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp()
                    bookshelfViewModel.resetUiState()
                }
            )
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = BookshelfScreen.HomeScreen.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable(route = BookshelfScreen.HomeScreen.name) {
                HomeScreen(
                    searchQuery = bookshelfViewModel.searchQuery,
                    onQueryChange = bookshelfViewModel::onQueryChange,
                    onSearchButtonClicked = {
                        bookshelfViewModel.searchBooks(
                            query = bookshelfViewModel.searchQuery,
                            onSuccess = {
                                navController.navigate(route = BookshelfScreen.ResultScreen.name)
                            }
                        )
                    },
                    uiState = uiState
                )
            }
            composable(route = BookshelfScreen.ResultScreen.name) {
                val queryResult = (uiState as BookshelfUiState.Success).booksQueryResult
                ResultScreen(
                    queryResult = queryResult
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfTopAppBar(
    currentScreenName: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = currentScreenName,
                fontWeight = FontWeight.Bold,
                //            style = MaterialTheme.typography.displaySmall
            )
        },
        modifier = modifier,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

enum class BookshelfScreen(@StringRes val title: Int) {
    HomeScreen(title = R.string.app_name),
    ResultScreen(title = R.string.result_found)
}