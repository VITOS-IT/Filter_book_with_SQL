package com.example.myroomapp

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
        ForeignKey(entity = Author::class,
            parentColumns = ["id"],
            childColumns = ["authorId"],
            onDelete = CASCADE)])
data class Book(
    @PrimaryKey(autoGenerate = true) val bookId:Int,
    val authorId: Int,
    var country: String,
    var imageLink: String,
    var language: String,
    var link: String,
    var pages: Int,
    var title: String ,
    var year: Int

)
