package com.example.bookshelf.data

import com.example.bookshelf.model.Book
import com.example.bookshelf.network.BooksQueryResult
import com.example.bookshelf.network.BookshelfApiService

interface BookRepository {
    suspend fun searchBooks(query: String): BooksQueryResult

    suspend fun getBook(bookId: String): Book
}

class NetworkBookRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookRepository{
    override suspend fun searchBooks(query: String): BooksQueryResult =
        bookshelfApiService.searchBooks(query)

    override suspend fun getBook(bookId: String): Book =
        bookshelfApiService.getBook(bookId)
}
