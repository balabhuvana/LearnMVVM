package com.example.learnmvvm.network

import com.google.gson.annotations.SerializedName

class UserModelForPostRequest {

    @SerializedName("name")
    var userName: String? = null
    @SerializedName("job")
    var userJob: String? = null

}