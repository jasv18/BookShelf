package com.example.bookshelf.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.bookshelf.R
import com.example.bookshelf.data.model.Book

@Composable
fun HomeScreen (
    uiState: BooksUiState,
    retryAction: () -> Unit,
    selectAction: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is BooksUiState.Success -> VerticalGridView(
            bookList = uiState.books,
            selectAction = selectAction
        )
        is BooksUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        else -> ErrorScreen(
            retryAction = retryAction,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun BooksScreen(
    modifier: Modifier = Modifier
) {

}

@Composable
fun BooksDetailedScreen(
    modifier: Modifier = Modifier
) {

}

@Composable
fun VerticalGridView(
    bookList: List<Book>,
    selectAction: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
        items(items = bookList, key = { doc ->  doc.id!! }) {
            doc -> BookCard(
                onClick = { selectAction(doc.id!!) },
                bookThumbnailUrl = doc.coverUrl.mediumUrl,
                modifier = Modifier
                    .widthIn(max = 200.dp)
                    .aspectRatio(2f/3f),
                context = context
            )
        }
    }
}

@Composable
fun BookCard(
    onClick: () -> Unit,
    bookThumbnailUrl: String?,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    Card {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(bookThumbnailUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = onClick
                )
        )
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = stringResource(R.string.loading_failed)
        )
        Text(
            stringResource(R.string.loading_failed)
        )
        Button(onClick = retryAction) {
            Text(
                stringResource(R.string.retry)
            )
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading),
            modifier = Modifier.size(200.dp)
        )
    }
}