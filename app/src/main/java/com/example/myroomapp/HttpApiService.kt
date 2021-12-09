package com.example.myroomapp

import retrofit2.http.GET


interface HttpApiService {
    @GET("/books")
    suspend fun getBookList(): List<IpResultBook>
}