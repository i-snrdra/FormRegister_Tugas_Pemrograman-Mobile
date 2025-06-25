package com.isa.tugas3

import retrofit2.http.GET

interface APIInterface {
    @GET("users")
    suspend fun getUsers(): UserResponse

    @GET("products")
    suspend fun getProducts(): ProductResponse
}