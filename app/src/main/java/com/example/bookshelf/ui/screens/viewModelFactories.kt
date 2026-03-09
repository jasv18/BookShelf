package com.example.bookshelf.ui.screens

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.data.repository.BookShelfRepository

object viewModelFactories {
    fun BookShelfViewModelFactory(
        bookShelfRepository: BookShelfRepository
    ): ViewModelProvider.Factory = viewModelFactory {
        initializer {
            BookShelfViewModel(bookShelfRepository)
        }
    }
}