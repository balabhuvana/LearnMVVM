package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.room.User
import com.example.myapplication.room.UserDao


class UserRepository(private var userDao: UserDao) {

    private var userListData: LiveData<List<User>> =
        MutableLiveData<List<User>>()

    private var userLiveData: LiveData<User>? = null

    fun insertUserRecord(user: User) {
        userDao.insertUser(user)
    }

    fun selectUserList(): LiveData<List<User>> {
        userListData = userDao.getUserList()
        return userListData
    }

    fun selectUser(rollNo: Int): LiveData<User> {
        userLiveData = userDao.getUserRecord(rollNo)
        return userLiveData as LiveData<User>
    }

    fun selectUserForDelete(rollNo: Int): User {
        return userDao.getUserRecordForDelete(rollNo)
    }

    fun deleteSpecificUser(user: User) {
        if (user != null) {
            userDao.deleteUserRecord(user)
        }
    }

    fun deleteAllUser() {
        userDao.deleteAllUser()
    }
}