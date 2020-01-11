package com.example.learnmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.learnmvvm.network.UserModel
import com.example.learnmvvm.network.UserModelRoot
import com.example.learnmvvm.repository.UserRepository
import com.example.learnmvvm.room.User
import com.example.learnmvvm.room.UserDao
import com.example.learnmvvm.room.UserRoomDatabase

class UserProfileViewModel(application: Application) : AndroidViewModel(application) {
    private var userRepository: UserRepository? = null
    private var userObserverLiveData: LiveData<List<User>>? = null
    private var userLiveData: LiveData<User>? = null
    private var userModelRoot: LiveData<UserModelRoot>? = null

    init {
        val userRoomDatabase: UserRoomDatabase =
            application.let { UserRoomDatabase.getDatabase(it) }
        val userDao: UserDao = userRoomDatabase.userDao()
        userRepository = UserRepository(userDao)
    }

    fun insertUserRecord(user: User) {
        userRepository?.insertUserRecord(user)
    }

    fun selectUserList() {
        userObserverLiveData = userRepository?.selectUserList()
    }

    fun selectSpecificUser(rollNo: Int) {
        userLiveData = userRepository?.selectUser(rollNo)
    }

    fun selectUserFromNetwork() {
        userModelRoot = userRepository?.fetchUserDataFromNetwork()
    }

    fun deleteSpecificUser(rollNo: Int) {
        val user: User? = userRepository?.selectUserForDelete(rollNo)
        user?.let { userRepository?.deleteSpecificUser(it) }
    }

    fun deleteAllUser() {
        userRepository?.deleteAllUser()
    }

    fun getUserListObservable(): LiveData<List<User>>? {
        return userObserverLiveData
    }

    fun getUserObservable(): LiveData<User>? {
        return userLiveData
    }

    fun getUserRootModelFromNetworkObservable(): LiveData<UserModelRoot>? {
        return userModelRoot
    }
}