package com.example.bookshelf

import android.app.Application
import com.example.bookshelf.data.AppContainer
import com.example.bookshelf.data.DefaultAppContainer

class BookshelfApplication : Application() {
    lateinit var dependencyContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        dependencyContainer = DefaultAppContainer()
    }
}