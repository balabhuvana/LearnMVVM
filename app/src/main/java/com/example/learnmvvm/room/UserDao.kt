package com.example.learnmvvm.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.learnmvvm.persistance.PersistenceUser
import com.example.learnmvvm.persistance.PersistenceUserRoot

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPersistenceUser(persistenceUser: PersistenceUser)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPersistenceUserRoot(persistenceUserRoot: PersistenceUserRoot)

    @Query("SELECT * from user_table")
    fun getUserList(): LiveData<List<User>>

    @Query("SELECT * from user_table where user_id = :rollNo LIMIT 1")
    fun getUserRecord(rollNo: Int): LiveData<User>

    @Query("SELECT * from user_table where user_id = :rollNo LIMIT 1")
    fun getUserRecordForDelete(rollNo: Int): User

    @Query("SELECT * from persistence_user_table")
    fun retrievePersistenceUser(): LiveData<PersistenceUser>

    @Query("SELECT * from persistence_user_root_table")
    fun retrievePersistenceUserRoot(): LiveData<PersistenceUserRoot>

    @Query("SELECT * from persistence_user_root_table where user_id=:userId LIMIT 1")
    fun retrievePersistenceUserRootWithUserId(userId: Int): LiveData<PersistenceUserRoot>

    @Delete
    fun deleteUserRecord(user: User)

    @Query("DELETE FROM user_table")
    fun deleteAllUser()

}