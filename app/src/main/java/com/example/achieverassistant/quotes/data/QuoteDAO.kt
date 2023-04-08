package com.example.achieverassistant.quotes.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDAO {
    @Insert
    fun insertQuote(quote: Quote)

    @Update
    fun updateQuote(quote: Quote)

    @Delete
    fun deleteQuote(quote: Quote)

    @Query("DELETE FROM Quote")
    fun deleteAllQuotes()


    @Query("SELECT * FROM Quote where quoteMember = :memberStatus")
    fun filterAllQuotes(memberStatus: String): Flow<List<Quote>>

    @Query("SELECT * FROM Quote")
    fun allQuotes(): Flow<List<Quote>>
}