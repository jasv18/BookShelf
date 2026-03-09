package com.example.bookshelf.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BookshelfResponse(
    val docs: List<BookDTO>
)