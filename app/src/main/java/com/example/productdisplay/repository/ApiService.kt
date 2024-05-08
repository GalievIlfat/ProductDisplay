package com.example.productdisplay.repository

import com.example.productdisplay.model.Products
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getAllProducts(@Query("skip") skip:Int,@Query("limit") limit:Int): Products
}