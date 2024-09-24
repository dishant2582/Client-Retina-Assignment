package com.testing.clickretina

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("b/6HBE") // Adjust the endpoint path as necessary
    fun getData(): Call<ApiResponse>
}
