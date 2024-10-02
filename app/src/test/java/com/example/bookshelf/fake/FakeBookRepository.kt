package com.example.bookshelf.fake

import com.example.bookshelf.data.BookRepository
import com.example.bookshelf.model.Book
import com.example.bookshelf.network.BooksQueryResult
import com.example.bookshelf.network.BookshelfApiService

class FakeBookRepository : BookRepository {
    override suspend fun searchBooks(query: String): BooksQueryResult =
        FakeDataSource.queryResult

    override suspend fun getBook(bookId: String): Book =
        FakeDataSource.queryResult.items[0]
}