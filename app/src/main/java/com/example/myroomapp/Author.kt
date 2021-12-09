package com.example.myroomapp

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "author_table", indices = [Index(value = ["authorName"], unique = true)])
data class Author(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val authorName:String
)
