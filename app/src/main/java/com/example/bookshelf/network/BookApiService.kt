package com.example.bookshelf.network

import com.example.bookshelf.data.model.BookshelfResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {
    @GET("search.json")
    suspend fun getBookShelf(
        @Query("q") q: String = "",
        @Query("fields") fields: String = ""
    ): BookshelfResponse
}
