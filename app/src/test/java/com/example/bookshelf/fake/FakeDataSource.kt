package com.example.bookshelf.fake

import com.example.bookshelf.model.Book
import com.example.bookshelf.model.ImageLinks
import com.example.bookshelf.model.VolumeInfo
import com.example.bookshelf.network.BooksQueryResult

object FakeDataSource {
    val queryResult: BooksQueryResult = BooksQueryResult(
        kind = "books#volumes",
        totalItems = 2,
        items = listOf(
            Book(
                id = "Book001",
                selfLink = "",
                volumeInfo = VolumeInfo(
                    title = "Book 001",
                    authors = listOf("Alpha Apple"),
                    publisher = "Fruit Publisher",
                    imageLinks = ImageLinks()
                )
            ),
            Book(
                id = "Book002",
                selfLink = "",
                volumeInfo = VolumeInfo(
                    title = "Book 002",
                    authors = listOf("Beta Banana", "Omega Orange"),
                    publisher = "Fruity Publisher",
                    imageLinks = ImageLinks()
                )
            )
        )
    )
}