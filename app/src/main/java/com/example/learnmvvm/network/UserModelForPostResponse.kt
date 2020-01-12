package com.example.learnmvvm.network

import com.google.gson.annotations.SerializedName

class UserModelForPostResponse {

    @SerializedName("name")
    var userName: String? = null
    @SerializedName("job")
    var userJob: String? = null
    @SerializedName("id")
    var userId: String? = null
    @SerializedName("createdAt")
    var userCreatedAt: String? = null

}