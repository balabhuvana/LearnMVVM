package com.example.learnmvvm.persistance

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "persistence_user_table")
class PersistenceUser {

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var id: Int? = 0

    @SerializedName("email")
    @ColumnInfo(name = "user_email")
    var email: String? = null

    @SerializedName("first_name")
    @ColumnInfo(name = "user_first_name")
    var firstName: String? = null

    @SerializedName("last_name")
    @ColumnInfo(name = "user_last_name")
    var lastName: String? = null

    @SerializedName("avatar")
    @ColumnInfo(name = "user_avatar")
    var avatar: String? = null

}