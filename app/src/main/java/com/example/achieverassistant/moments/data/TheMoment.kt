package com.example.achieverassistant.moments.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TheMoment(var image: String, var title: String, var date: String, var descripton: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}