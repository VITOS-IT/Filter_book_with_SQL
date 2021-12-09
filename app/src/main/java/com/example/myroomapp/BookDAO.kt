package com.example.myroomapp

import androidx.room.*


@Dao
interface BookDAO {
    @Query("SELECT * FROM author_table")
    fun getAllAuthor(): List<Author>
    @Query("SELECT * FROM author_table where authorName = :autName")
    fun getAuthorByName(autName:String): Author

    @Query("SELECT authorName FROM author_table WHERE id =:authorId")
    fun getAuthorById(authorId: Int): String

    @Query("SELECT book.title from author_table JOIN Book on author_table.id = book.authorId"
            +" WHERE author_table.authorName = :autName or book.language = :language")
        fun getRequest(autName:String, language:String):List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthor(author: Author)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book)

    @Delete
    fun delete(author: Author)
}