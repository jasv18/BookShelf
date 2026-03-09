package com.example.bookshelf.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.data.model.Book
import com.example.bookshelf.data.model.asDomain
import com.example.bookshelf.data.repository.BookShelfRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed interface BooksUiState {
    data class Success(val books: List<Book>): BooksUiState
    data object Loading: BooksUiState
    data object Error: BooksUiState
}

class BookShelfViewModel(
    private val bookShelfRepository: BookShelfRepository
): ViewModel() {
    private val _bookShelf = MutableStateFlow<BooksUiState>(BooksUiState.Loading)

    val bookShelf: StateFlow<BooksUiState> = _bookShelf.asStateFlow()

    init {
        getBooks()
    }

    fun getBooks() {
        viewModelScope.launch {
            _bookShelf.update {
                try {
                    val response = bookShelfRepository.getBookShelf(
                        "jazz+history",
                        "title,author_name,cover_i,first_publish_year"
                    ).docs
                        .map { it.asDomain() }
                        .filter { it.id != null }
                    BooksUiState.Success(response)
                } catch (e: IOException) {
                    BooksUiState.Error
                } catch (e: HttpException) {
                    Log.e("BookShelfVM", "HTTP ${e.code()} ${e.message()}")
                    Log.e("BookShelfVM", "ErrorBody: ${e.response()?.errorBody()?.string()}")
                    BooksUiState.Error
                }
            }
        }
    }

    fun getBookById(bookId: Int) {

    }
}