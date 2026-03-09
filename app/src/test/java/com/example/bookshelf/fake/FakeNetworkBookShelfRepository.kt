package com.example.bookshelf.fake

import com.example.bookshelf.data.model.BookshelfResponse
import com.example.bookshelf.data.repository.BookShelfRepository

class FakeNetworkBookShelfRepository: BookShelfRepository {
    override suspend fun getBookShelf(
        q: String,
        fields: String
    ): BookshelfResponse = FakeDataSource.bookshelfResponse
}