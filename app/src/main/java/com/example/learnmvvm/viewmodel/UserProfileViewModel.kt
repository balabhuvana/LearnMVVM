package com.example.learnmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.learnmvvm.network.UserListModelRoot
import com.example.learnmvvm.network.UserModelForPostRequest
import com.example.learnmvvm.network.UserModelForPostResponse
import com.example.learnmvvm.network.UserModelRoot
import com.example.learnmvvm.persistance.PersistenceUser
import com.example.learnmvvm.persistance.PersistenceUserRoot
import com.example.learnmvvm.repository.UserRepository
import com.example.learnmvvm.room.User
import com.example.learnmvvm.room.UserDao
import com.example.learnmvvm.room.UserRoomDatabase

class UserProfileViewModel(application: Application) : AndroidViewModel(application) {
    private var userRepository: UserRepository? = null
    private var userObserverLiveData: LiveData<List<User>>? = null
    private var userLiveData: LiveData<User>? = null
    private var userModelRoot: LiveData<UserModelRoot>? = null
    private var userModelListLiveData: LiveData<UserListModelRoot>? = null
    private var userModelForPostResponse: LiveData<UserModelForPostResponse>? = null
    private var persistenceUserLiveData: LiveData<PersistenceUser>? = null
    private var persistenceUserRootLiveData: LiveData<PersistenceUserRoot>? = null
    private var tryPersistenceUserRootLiveDataFromRoom: LiveData<PersistenceUserRoot>? = null
    private var tryPersistenceUserRootLiveDataFromNetwork: LiveData<PersistenceUserRoot>? = null

    init {
        val userRoomDatabase: UserRoomDatabase =
            application.let { UserRoomDatabase.getDatabase(it) }
        val userDao: UserDao = userRoomDatabase.userDao()
        userRepository = UserRepository(userDao)
    }

    fun insertUserRecord(user: User) {
        userRepository?.insertUserRecord(user)
    }

    fun insertPersistenceUserWithRoom(persistenceUser: PersistenceUser) {
        userRepository?.insertPersistenceUserInRoom(persistenceUser)
    }

    fun insertPersistenceUserRootWithRoom(persistenceUserRoot: PersistenceUserRoot) {
        userRepository?.insertPersistenceUserRootInRoom(persistenceUserRoot)
    }

    fun tryPersistenceUserRootFromRoom(userId: Int) {

        tryPersistenceUserRootLiveDataFromRoom = userRepository?.tryFetchPersistenceUserRootInRoom()

        // This is for getting record based on user id
       /* tryPersistenceUserRootLiveDataFromRoom =
            userRepository?.tryFetchPersistenceUserRootInRoomByUserId(userId)*/
    }

    fun tryPersistenceUserRootFromNetwork() {
        tryPersistenceUserRootLiveDataFromNetwork =
            userRepository?.tryFetchPersistenceUserRootFromNetworkAndInsertIntoRoom()
    }

    fun retrievePersistenceUserFromRoom() {
        persistenceUserLiveData = userRepository?.retrievePersistenceUserFromRoom()
    }

    fun retrievePersistenceUserRootFromRoom() {
        persistenceUserRootLiveData = userRepository?.retrievePersistenceUserRootInRoom()
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

    fun selectUserFromNetworkWithCacheSupport() {
        userModelRoot = userRepository?.fetchUserDataFromNetworkWithCacheSupport(2)
    }

    fun fetchUserListFromNetwork() {
        userModelListLiveData = userRepository?.fetchUserListFromNetwork()
    }

    fun fetchUserModelPostRequestFromNetwork(userModelForPostRequest: UserModelForPostRequest) {
        userModelForPostResponse =
            userRepository?.fetchUserModelPostFromNetwork(userModelForPostRequest)
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

    fun fetchUserListFromNetworkObservable(): LiveData<UserListModelRoot>? {
        return userModelListLiveData
    }

    fun fetchUserModelPostRequestFromNetworkObservable(): LiveData<UserModelForPostResponse>? {
        return userModelForPostResponse
    }

    fun fetchPersistenceUserWithRoomObservable(): LiveData<PersistenceUser>? {
        return persistenceUserLiveData;
    }

    fun fetchPersistenceUserRootWithRoomObservable(): LiveData<PersistenceUserRoot>? {
        return persistenceUserRootLiveData;
    }

    fun tryFetchPersistenceUserRootFromRoomObservable(): LiveData<PersistenceUserRoot>? {
        return tryPersistenceUserRootLiveDataFromRoom
    }

    fun tryFetchPersistenceUserRootFromNetworkObservable(): LiveData<PersistenceUserRoot>? {
        return tryPersistenceUserRootLiveDataFromNetwork
    }
}