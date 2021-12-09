package com.example.myroomapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = Room.databaseBuilder(
            applicationContext,
            BookDatabase::class.java, "database-name"
        ).build()
        val bookDao = db.bookDao()
        val authorView = findViewById<EditText>(R.id.authorEditText)
        val languageView = findViewById<EditText>(R.id.languageEditText)
        val filterBtn = findViewById<Button>(R.id.filterBtn)

        var resultCountViev = findViewById<TextView>(R.id.resultCountView)
        var listOutputViev = findViewById<TextView>(R.id.listOutputView)

        val myApplication = application as MyBookApp
        val httpApiService = myApplication.httpApiService

        CoroutineScope(Dispatchers.IO).launch {
            val decodedJsonResult = httpApiService.getBookList()
            val bookList = decodedJsonResult.toList()
            for (it in bookList) {
                val author = Author(0, it.author)
                bookDao.insertAuthor(author)
            }
            for (it in bookList) {
                val autId = bookDao.getAuthorByName(it.author).id
                val book = Book(
                    0,
                    autId,
                    it.country,
                    it.imageLink,
                    it.language,
                    it.link,
                    it.pages,
                    it.title,
                    it.year
                )
                bookDao.insertBook(book)
            }
        }

        filterBtn.setOnClickListener {
            var author = authorView.text?.toString()
            var language = languageView.text?.toString()

            var maskedAuthor = if (author.isNullOrEmpty()) ""
            else author
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

            var maskedLang = if (language.isNullOrEmpty()) ""
            else language
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

            CoroutineScope(Dispatchers.IO).launch {
                val filterBookList = bookDao.getRequest(maskedAuthor, maskedLang)

                val booksAsString = StringBuilder("")

                for (item in filterBookList.take(3)) {
                    booksAsString.append("Result: " + item + "\n")

                }
                withContext(Dispatchers.Main) {
                    resultCountViev.text = "Results: ${filterBookList.size}"
                    listOutputViev.text = booksAsString
                }
            }

        }

    }

    fun loadAllBook() {
        val myApplication = application as MyBookApp
        val httpApiService = myApplication.httpApiService
        val bookDAO: BookDAO

        CoroutineScope(Dispatchers.IO).launch {
            val decodedJsonResult = httpApiService.getBookList()
            val bookList = decodedJsonResult.toList()
            withContext(Dispatchers.Main) {
                var i = 1
                for (item in bookList) {
                    val author = Author(0, item.author)
//                   bookDAO.insertAuthor(author)
                }
            }
        }
    }
}
