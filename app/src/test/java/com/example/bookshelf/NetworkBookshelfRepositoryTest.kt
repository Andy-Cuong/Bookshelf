package com.example.bookshelf

import com.example.bookshelf.data.NetworkBookRepository
import com.example.bookshelf.fake.FakeBookshelfApiService
import com.example.bookshelf.fake.FakeDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkBookshelfRepositoryTest {
    @Test
    fun networkBookshelfRepository_searchBooks_verifyQueryResult() {
        runTest {
            val bookRepository = NetworkBookRepository(
                bookshelfApiService = FakeBookshelfApiService()
            )
            assertEquals(FakeDataSource.queryResult, bookRepository.searchBooks("Book"))
        }
    }
}