package com.example.learnmvvm.network

import com.example.learnmvvm.persistance.PersistenceUserRoot
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiWebService {
    @GET("api/users/2")
    fun getUser(): Call<UserModelRoot>

    @GET("api/users?page=2")
    fun getUserList(): Call<UserListModelRoot>

    @POST("api/users")
    fun getUserDetailUsingPost(@Body userModelForPost: UserModelForPostRequest): Call<UserModelForPostResponse>

    @GET("api/users/2")
    fun getPersistenceUserRootLiveData(): Call<PersistenceUserRoot>
}