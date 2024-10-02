package com.example.bookshelf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bookshelf.R
import com.example.bookshelf.ui.theme.BookshelfTheme

@Composable
fun HomeScreen(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onSearchButtonClicked: () -> Unit,
    uiState: BookshelfUiState,
    modifier: Modifier = Modifier
) {
    val searchError = (uiState == BookshelfUiState.Error)

    when (uiState) {
        BookshelfUiState.Loading -> LoadingScreen(
            modifier = modifier
        )
        else -> SearchScreen(
            searchQuery = searchQuery,
            onQueryChange = onQueryChange,
            onSearchButtonClicked = onSearchButtonClicked,
            searchError = searchError,
            modifier = modifier
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.ic_searching),
        contentDescription = stringResource(R.string.searching),
        modifier = modifier.size(200.dp)
    )
}

@Composable
fun SearchScreen(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onSearchButtonClicked: () -> Unit,
    searchError: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = searchQuery,
                onValueChange = onQueryChange,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                label = { Text(stringResource(R.string.enter_search_term)) },
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchButtonClicked() }
                )
            )
            IconButton(
                onClick = onSearchButtonClicked,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                enabled = searchQuery != "",
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            }
        }
        
        if (searchError) {
            Text(
                text = stringResource(R.string.error_please_try_again),
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPrev() {
    BookshelfTheme {
        SearchScreen(
            searchQuery = "Book",
            onQueryChange = {},
            onSearchButtonClicked = {},
            searchError = true
        )
    }
}