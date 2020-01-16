package com.example.learnmvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.learnmvvm.cache.UserCache
import com.example.learnmvvm.network.*
import com.example.learnmvvm.persistance.PersistenceUser
import com.example.learnmvvm.persistance.PersistenceUserRoot
import com.example.learnmvvm.room.User
import com.example.learnmvvm.room.UserDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository(private var userDao: UserDao) {

    private var userListData: LiveData<List<User>> =
        MutableLiveData<List<User>>()

    private var userLiveData: LiveData<User>? = null

    private var retrofitService = RetrofitService()

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

        val userModelRootMutableLiveData: MutableLiveData<UserModelRoot> = MutableLiveData()
        val callUserModel: Call<UserModelRoot> = retrofitService.userApi.getUser()

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
        val mutableUserList = MutableLiveData<UserListModelRoot>()
        val callUserModelList = retrofitService.userApi.getUserList()
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

    fun fetchUserModelPostFromNetwork(userModelForPost: UserModelForPostRequest): LiveData<UserModelForPostResponse> {

        val modelForPostResponse = MutableLiveData<UserModelForPostResponse>()

        val callUserModelForPostRequest =
            retrofitService.userApi.getUserDetailUsingPost(userModelForPost)

        callUserModelForPostRequest.enqueue(object : Callback<UserModelForPostResponse> {
            override fun onFailure(call: Call<UserModelForPostResponse>, t: Throwable) {
                Log.i("---> ", " onFailure " + t.localizedMessage)
            }

            override fun onResponse(
                call: Call<UserModelForPostResponse>,
                response: Response<UserModelForPostResponse>
            ) {
                modelForPostResponse.postValue(response.body())
            }
        })

        return modelForPostResponse
    }

    /**
     * This id we are not actually using for network operation
     */
    fun fetchUserDataFromNetworkWithCacheSupport(userId: Int): LiveData<UserModelRoot> {

        val userCache: LiveData<UserModelRoot>? = UserCache.userCacheHashMap.get(userId)
        if (userCache != null) {
            Log.i("-----> ", "UserRepository - From Cache")
            return userCache
        }

        val userModelRootMutableLiveData: MutableLiveData<UserModelRoot> = MutableLiveData()
        UserCache.userCacheHashMap.put(userId, userModelRootMutableLiveData)
        val callUserModel: Call<UserModelRoot> = retrofitService.userApi.getUser()

        callUserModel.enqueue(object : Callback<UserModelRoot> {
            override fun onFailure(call: Call<UserModelRoot>, t: Throwable) {
                Log.i("---> ", " onFailure " + t.localizedMessage)
            }

            override fun onResponse(call: Call<UserModelRoot>, response: Response<UserModelRoot>) {
                Log.i("-----> ", "UserRepository - From Network")
                userModelRootMutableLiveData.value = response.body()
            }
        })
        return userModelRootMutableLiveData
    }

    fun insertPersistenceUserInRoom(persistenceUser: PersistenceUser) {
        userDao.insertPersistenceUser(persistenceUser)
    }

    fun retrievePersistenceUserFromRoom(): LiveData<PersistenceUser> {
        var persistenceUserLiveData = userDao.retrievePersistenceUser()
        return persistenceUserLiveData
    }

    fun insertPersistenceUserRootInRoom(persistenceUserRoot: PersistenceUserRoot) {
        userDao.insertPersistenceUserRoot(persistenceUserRoot)
    }

    fun retrievePersistenceUserRootInRoom(): LiveData<PersistenceUserRoot> {
        return userDao.retrievePersistenceUserRoot()
    }

}