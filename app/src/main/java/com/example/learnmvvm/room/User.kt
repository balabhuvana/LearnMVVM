package com.example.learnmvvm.room

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var userId: Int = 0

    @ColumnInfo(name = "user_name")
    var userName: String = ""

    @ColumnInfo(name = "user_age")
    var userAge: Int = 0

    @ColumnInfo(name = "user_place")
    var userPlace: String = ""
}