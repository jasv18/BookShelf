package com.example.bookshelf.data.repository

import com.example.bookshelf.data.model.BookshelfResponse
import com.example.bookshelf.network.BooksApiService

interface BookShelfRepository {
    suspend fun getBookShelf(q: String, fields: String): BookshelfResponse
}

class NetworkBookShelfRepository(private val service: BooksApiService): BookShelfRepository {
    override suspend fun getBookShelf(
        q: String,
        fields: String
    ) = service.getBookShelf(q, fields)
}