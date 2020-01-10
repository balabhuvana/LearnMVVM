package com.example.learnmvvm.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Query("SELECT * from user_table")
    fun getUserList(): LiveData<List<User>>

    @Query("SELECT * from user_table where user_id = :rollNo LIMIT 1")
    fun getUserRecord(rollNo: Int): LiveData<User>

    @Query("SELECT * from user_table where user_id = :rollNo LIMIT 1")
    fun getUserRecordForDelete(rollNo: Int): User

    @Delete
    fun deleteUserRecord(user: User)

    @Query("DELETE FROM user_table")
    fun deleteAllUser()
}