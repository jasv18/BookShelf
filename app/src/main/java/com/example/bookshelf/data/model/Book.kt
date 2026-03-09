package com.example.bookshelf.data.model

data class Book (
    val id: Int?,
    val title: String?,
    val author: List<String>?,
    val year: Int?,
    val coverUrl: CoverUrls
)
