package com.example.bookshelf.data

import com.example.bookshelf.data.repository.BookShelfRepository
import com.example.bookshelf.data.repository.NetworkBookShelfRepository
import com.example.bookshelf.network.BooksApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

interface AppContainer {
    val bookShelfRepository: BookShelfRepository
}

class DefaultAppContainer: AppContainer {

    private val baseUrl0 = "https://www.googleapis.com/books/v1/"
    private val baseUrl1 = "https://openlibrary.org/"

    private val formatJson = Json {
        ignoreUnknownKeys = true
        coerceInputValues= true
        explicitNulls = false
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(formatJson.asConverterFactory("application/json; charset=UTF-8".toMediaType()))
        .baseUrl(baseUrl1)
        .build()

    private val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }

    override val bookShelfRepository by lazy { NetworkBookShelfRepository(retrofitService) }
}