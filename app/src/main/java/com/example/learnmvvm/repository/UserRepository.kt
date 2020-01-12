package com.example.learnmvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.learnmvvm.network.RetrofitService
import com.example.learnmvvm.network.UserListModelRoot
import com.example.learnmvvm.network.UserModel
import com.example.learnmvvm.network.UserModelRoot
import com.example.learnmvvm.room.User
import com.example.learnmvvm.room.UserDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository(private var userDao: UserDao) {

    private var userListData: LiveData<List<User>> =
        MutableLiveData<List<User>>()

    private var userLiveData: LiveData<User>? = null

    var retrofitService = RetrofitService()

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
        userDao.deleteUserRecord(user)
    }

    fun deleteAllUser() {
        userDao.deleteAllUser()
    }

    fun fetchUserDataFromNetwork(): LiveData<UserModelRoot> {

        var userModelRootMutableLiveData: MutableLiveData<UserModelRoot> = MutableLiveData()
        var callUserModel: Call<UserModelRoot> = retrofitService.userApi.getUser()

        callUserModel.enqueue(object : Callback<UserModelRoot> {
            override fun onFailure(call: Call<UserModelRoot>, t: Throwable) {
                Log.i("---> ", " onFailure " + t.localizedMessage)
            }

            override fun onResponse(call: Call<UserModelRoot>, response: Response<UserModelRoot>) {
                Log.i("---> ", " onResponse ")
                userModelRootMutableLiveData.postValue(response.body())
            }
        })
        return userModelRootMutableLiveData
    }

    fun fetchUserListFromNetwork(): LiveData<UserListModelRoot> {
        var mutableUserList = MutableLiveData<UserListModelRoot>()
        var callUserModelList = retrofitService.userApi.getUserList()
        callUserModelList.enqueue(object : Callback<UserListModelRoot> {
            override fun onFailure(call: Call<UserListModelRoot>, t: Throwable) {
                Log.i("---> ", " onFailure " + t.localizedMessage)
            }

            override fun onResponse(
                call: Call<UserListModelRoot>,
                response: Response<UserListModelRoot>
            ) {
                mutableUserList.postValue(response.body())
            }
        })

        return mutableUserList
    }
}