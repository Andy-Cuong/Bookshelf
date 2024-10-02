package com.example.bookshelf.ui.screens

import android.R.attr.author
import android.R.attr.text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import com.example.bookshelf.network.BooksQueryResult

@Composable
fun ResultScreen(
    queryResult: BooksQueryResult,
    modifier: Modifier = Modifier
) {
    val bookList = queryResult.items

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        items(items = bookList, key = { book -> book.id }) { book ->
            BookCard(book = book)
        }
    }
}

@Composable
fun BookCard(book: Book, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small)),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            BookThumbnail(book = book)
            BookInfo(book = book)
        }
    }
}

@Composable
fun BookThumbnail(book: Book, modifier: Modifier = Modifier) {
    val thumbnailUrl = if (!book.volumeInfo.imageLinks?.thumbnail.isNullOrBlank()) {
        book.volumeInfo.imageLinks.thumbnail.replace("http", "https")
    } else {
        null
    }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(thumbnailUrl)
            .crossfade(enable = true)
            .build(),
        contentDescription = book.volumeInfo.title,
        modifier = modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_loading_image),
        error = painterResource(R.drawable.ic_broken_image)
    )
}

@Composable
fun BookInfo(book: Book, modifier: Modifier = Modifier) {
    val title = book.volumeInfo.title
    val authorIterator = book.volumeInfo.authors?.listIterator()
    val authors = if (authorIterator != null) {
        var tmp = ""
        while (authorIterator.hasNext()) {
            tmp += authorIterator.next()
            if (authorIterator.hasNext()) {
                tmp += ", "
            }
        }
        tmp
    } else {
        stringResource(R.string.unknown)
    }
    val publisher = book.volumeInfo.publisher ?: stringResource(R.string.unknown)

    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.authors, authors),
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = stringResource(R.string.publisher, publisher),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}