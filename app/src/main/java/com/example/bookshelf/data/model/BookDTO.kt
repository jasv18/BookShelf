package com.example.bookshelf.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDTO (
    @SerialName("cover_i") val coverId: Int?,
    val title: String?,
    @SerialName("author_name") val author: List<String>?,
    @SerialName("first_publish_year") val year: Int?,
)

fun BookDTO.asDomain(): Book {
    return Book(
        id = this.coverId,
        title = this.title,
        author = this.author,
        year = this.year,
        coverUrl = CoverUrls(
            smallUrl = this.coverId.let { "https://covers.openlibrary.org/b/id/$it-S.jpg" },
            mediumUrl = this.coverId.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" },
            largeUrl = this.coverId.let { "https://covers.openlibrary.org/b/id/$it-L.jpg" }
        )
    )
}
