package com.example.achieverassistant.quotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(var quoteMember: String?, var quote: String?, var avatar: Int) {
    @PrimaryKey(autoGenerate = true)
    var quoteID = 0
}