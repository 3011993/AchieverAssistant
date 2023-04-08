package com.example.achieverassistant.quotes.data

import androidx.room.Database
import androidx.room.RoomDatabase



@Database(entities = [Quote::class], version = 1, exportSchema = false)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDAO(): QuoteDAO
}




