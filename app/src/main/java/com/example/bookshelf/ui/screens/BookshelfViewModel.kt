package com.example.bookshelf.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.data.BookRepository
import com.example.bookshelf.network.BooksQueryResult
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BookshelfViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {
    var bookshelfUiState: BookshelfUiState by mutableStateOf(BookshelfUiState.Start)
        private set
    var searchQuery: String by mutableStateOf("")

    fun onQueryChange(query: String) {
        searchQuery = query
    }

    fun searchBooks(query: String, onSuccess: () -> Unit) {
        bookshelfUiState = BookshelfUiState.Loading

        viewModelScope.launch {
            bookshelfUiState = try {
                val searchResult = bookRepository.searchBooks(query = query)
                BookshelfUiState.Success(booksQueryResult = searchResult)
            } catch (e: IOException) {
                BookshelfUiState.Error
            } catch (e: HttpException) {
                BookshelfUiState.Error
            }

            if (bookshelfUiState is BookshelfUiState.Success) {
                onSuccess()
            }
        }
    }

    fun resetUiState() {
        bookshelfUiState = BookshelfUiState.Start
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val bookRepository = application.dependencyContainer.bookRepository
                BookshelfViewModel(bookRepository = bookRepository)
            }
        }
    }
}

sealed interface BookshelfUiState {
    data object Start: BookshelfUiState
    data object Loading: BookshelfUiState
    data class Success(val booksQueryResult: BooksQueryResult): BookshelfUiState
    data object Error: BookshelfUiState
}