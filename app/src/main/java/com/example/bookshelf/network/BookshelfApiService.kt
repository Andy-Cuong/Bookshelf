package com.example.bookshelf.network

import com.example.bookshelf.model.Book
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfApiService {
    @GET("books/v1/volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResult: Int = 20 // max is 40
    ): BooksQueryResult

    @GET("books/v1/volumes/{id}")
    suspend fun getBook(@Path("id") bookId: String): Book
}

@Serializable
data class BooksQueryResult(
    val kind: String,
    val totalItems: Int,
    val items: List<Book>
)