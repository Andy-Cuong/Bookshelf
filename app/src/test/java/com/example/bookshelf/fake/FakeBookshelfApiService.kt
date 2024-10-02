package com.example.bookshelf.fake

import com.example.bookshelf.model.Book
import com.example.bookshelf.network.BooksQueryResult
import com.example.bookshelf.network.BookshelfApiService

class FakeBookshelfApiService : BookshelfApiService {
    override suspend fun searchBooks(
        query: String,
        maxResults: Int
    ): BooksQueryResult = FakeDataSource.queryResult

    override suspend fun getBook(bookId: String): Book =
        FakeDataSource.queryResult.items[0]
}