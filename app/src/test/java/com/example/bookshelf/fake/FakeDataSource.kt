package com.example.bookshelf.fake

import com.example.bookshelf.data.model.BookDTO
import com.example.bookshelf.data.model.BookshelfResponse

object Item {
    val docs= listOf(
        BookDTO(coverId = 1, title = "Titular 1", author = listOf("author 1"), 2020),
        BookDTO(coverId = 2, title = "Titular 2", author = listOf("author 2"), 2021),
        BookDTO(coverId = 3, title = "Titular 3", author = listOf("author 3"), 2022),
        BookDTO(coverId = 4, title = "Titular 4", author = listOf("author 4"), 2023),
        BookDTO(coverId = 5, title = "Titular 5", author = listOf("author 5"), 2024)
    )
}

object FakeDataSource {
    val bookshelfResponse = BookshelfResponse(Item.docs)
}