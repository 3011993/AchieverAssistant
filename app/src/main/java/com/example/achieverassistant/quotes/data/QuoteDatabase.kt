package com.example.achieverassistant.quotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

import androidx.room.Room
import com.example.achieverassistant.quotes.data.QuoteDAO


@Database(entities = [Quote::class], version = 4, exportSchema = false)
abstract class QuoteDatabase : RoomDatabase() {

    abstract fun quoteDAO(): QuoteDAO
}


private lateinit var INSTANCE: QuoteDatabase

fun getQuoteDatabase(context: Context): QuoteDatabase {
    synchronized(QuoteDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                QuoteDatabase::class.java,
                "quotesDatabase"
            )
                .fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}


