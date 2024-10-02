package com.example.bookshelf.data

import com.example.bookshelf.network.BookshelfApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bookRepository: BookRepository
}

class DefaultAppContainer : AppContainer {
    override val bookRepository: BookRepository by lazy {
        NetworkBookRepository(retrofitService)
    }

    private val baseUrl = "https://www.googleapis.com"

    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // Using lazy init to create the network object only when actually needed instead of from
    // the beginning, because this is an expensive task
    private val retrofitService by lazy {
        retrofit.create(BookshelfApiService::class.java)
    }
}