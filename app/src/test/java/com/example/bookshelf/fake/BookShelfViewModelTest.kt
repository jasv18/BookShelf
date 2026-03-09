package com.example.bookshelf.fake

import com.example.bookshelf.data.model.asDomain
import com.example.bookshelf.rules.TestDispatcherRule
import com.example.bookshelf.ui.screens.BookShelfViewModel
import com.example.bookshelf.ui.screens.BooksUiState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class BookShelfViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun bookShelfViewModel_getPhotos_verifyBookUiStateSuccess() = runTest {
        val bookViewModel = BookShelfViewModel(
            bookShelfRepository = FakeNetworkBookShelfRepository()
        )
        assertEquals(
            BooksUiState.Success(
                books = FakeDataSource.bookshelfResponse.docs.map {
                    it.asDomain()
                }
            ),
            bookViewModel.bookShelf.value
        )
    }
}