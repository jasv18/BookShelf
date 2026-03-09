package com.example.bookshelf.fake

import com.example.bookshelf.data.model.BookshelfResponse
import com.example.bookshelf.network.BooksApiService

class FakeBookShelfApiService: BooksApiService {
    override suspend fun getBookShelf(
        q: String,
        fields: String
    ): BookshelfResponse = FakeDataSource.bookshelfResponse
}