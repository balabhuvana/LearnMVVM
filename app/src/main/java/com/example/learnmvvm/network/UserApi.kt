package com.example.learnmvvm.network

import retrofit2.Call
import retrofit2.http.GET

interface UserApi {
    @GET("api/users/2")
    fun getUser(): Call<UserModelRoot>
}