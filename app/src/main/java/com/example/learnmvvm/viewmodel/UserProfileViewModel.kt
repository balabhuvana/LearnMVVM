package com.example.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.room.User
import com.example.myapplication.room.UserDao
import com.example.myapplication.room.UserRoomDatabase

class UserProfileViewModel(application: Application) : AndroidViewModel(application) {
    private var userRepository: UserRepository? = null
    private var userObserverLiveData: LiveData<List<User>>? = null
    private var userLiveData: LiveData<User>? = null

    init {
        val userRoomDatabase: UserRoomDatabase =
            application.let { UserRoomDatabase.getDatabase(it) }
        val userDao: UserDao = userRoomDatabase.userDao()
        userRepository = UserRepository(userDao)
    }

    fun insertUserRecord(user: User){
        userRepository?.insertUserRecord(user)
    }

    fun selectUserListFromRepository() {
        userObserverLiveData = userRepository?.selectUserList()
    }

    fun selectSpecificUserFromRepository(rollNo: Int) {
        userLiveData = userRepository?.selectUser(rollNo)
    }

    fun deleteSpecificUserFromRepository(rollNo: Int) {
        var user = userRepository?.selectUserForDelete(rollNo)
        Log.i("---> ", "" + user?.userName)
        user?.let { userRepository?.deleteSpecificUser(it) }

    }

    fun deleteAllUserFromRepository() {
        userRepository?.deleteAllUser()
    }

    fun getUserListObservable(): LiveData<List<User>>? {
        return userObserverLiveData
    }

    fun getUserObservable(): LiveData<User>? {
        return userLiveData
    }
}