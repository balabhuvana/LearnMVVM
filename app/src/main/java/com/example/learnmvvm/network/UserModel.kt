package com.example.learnmvvm.network

import com.google.gson.annotations.SerializedName

class UserModel {
    @SerializedName("id")
    var id: Int? = 0
    @SerializedName("email")
    var email: String? = null
    @SerializedName("first_name")
    var firstName: String? = null
    @SerializedName("last_name")
    var lastName: String? = null
    @SerializedName("avatar")
    var avatar: String? = null
}