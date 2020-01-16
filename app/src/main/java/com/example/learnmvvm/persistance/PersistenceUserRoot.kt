package com.example.learnmvvm.persistance

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "persistence_user_root_table")
class PersistenceUserRoot {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_root_id")
    var userId: Int? = 0

    @SerializedName("data")
    @Embedded
    var data: PersistenceUser? = null

}