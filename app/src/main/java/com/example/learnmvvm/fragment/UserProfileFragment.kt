package com.example.learnmvvm.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.learnmvvm.R
import com.example.learnmvvm.network.*
import com.example.learnmvvm.persistance.PersistenceUser
import com.example.learnmvvm.persistance.PersistenceUserRoot
import com.example.learnmvvm.room.User
import com.example.learnmvvm.viewmodel.UserProfileViewModel
import kotlinx.android.synthetic.main.fragment_user_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 */
class UserProfileFragment : Fragment() {

    private lateinit var userProfileViewModel: UserProfileViewModel
    private var TAG = UserProfileFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userProfileViewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)
        btnInsert.setOnClickListener {
            performInsertPersistenceUserRootInRoom()
        }

        btnSelectAllUser.setOnClickListener {
            userProfileViewModel.selectUserList()
            observeAllUserData(userProfileViewModel)
        }

        btnSelectSpecificUser.setOnClickListener {
            userProfileViewModel.selectUserFromNetworkWithCacheSupport()
            observeGetUserRootFromNetworkSupportCache(userProfileViewModel)
        }

        btnDeleteSpecificUser.setOnClickListener {
            GlobalScope.launch {
                userProfileViewModel.deleteSpecificUser(7)
            }
        }

        btnDeleteAllUser.setOnClickListener {
            val userModelForPostRequest = UserModelForPostRequest()
            userModelForPostRequest.userName = "Arunkumar V"
            userModelForPostRequest.userJob = "Cab Driver"
            userProfileViewModel.fetchUserModelPostRequestFromNetwork(userModelForPostRequest)
            observeUserModelPostResponseFromNetwork(userProfileViewModel)
        }

    }

    fun performInsertUser() {
        val user = User()
        user.userName = "Anand Kumar"
        user.userAge = 31
        user.userPlace = "Salem"
        GlobalScope.launch {
            userProfileViewModel.insertUserRecord(user)
        }
    }

    private fun performInsertPersistenceUserInRoom() {
        var persistenceUser = PersistenceUser()
        persistenceUser.firstName = "Arun Salem"
        persistenceUser.lastName = "V"
        persistenceUser.email = "arun.v@gmail.com"
        persistenceUser.id = 90001
        persistenceUser.avatar = "http://example.com"
        userProfileViewModel.insertPersistenceUserWithRoom(persistenceUser)
    }

    private fun performInsertPersistenceUserRootInRoom() {
        GlobalScope.launch {
            var persistenceUserRoot = PersistenceUserRoot()
            var persistenceUser = PersistenceUser()
            persistenceUser.firstName = "Arun Salem"
            persistenceUser.lastName = "V"
            persistenceUser.email = "arun.v@gmail.com"
            persistenceUser.id = 90001
            persistenceUser.avatar = "http://example.com"
            persistenceUserRoot.data = persistenceUser
            userProfileViewModel.insertPersistenceUserRootWithRoom(persistenceUserRoot)
        }
    }

    private fun performRetrievePersistenceUserFromRoom() {
        userProfileViewModel.retrievePersistenceUserFromRoom()
        observePersistenceUserFromRoom(userProfileViewModel)
    }

    private fun performRetrievePersistenceUserRootFromRoom() {
        userProfileViewModel.retrievePersistenceUserRootFromRoom()
        observerRetrievePersistenceUserRootInRoom(userProfileViewModel)
    }

    private fun observeSpecificUser(userProfileViewModel: UserProfileViewModel) {
        userProfileViewModel.getUserObservable()
            ?.observe(viewLifecycleOwner,
                Observer<User> { user ->
                    Log.i("ID : ", "" + user?.userId)
                    Log.i("Name : ", "" + user?.userName)
                    Log.i("Age : ", "" + user?.userAge)
                    Log.i("Place : ", "" + user?.userPlace)
                })
    }

    private fun observeAllUserData(userProfileViewModel: UserProfileViewModel) {
        userProfileViewModel.getUserListObservable()?.observe(
            viewLifecycleOwner,
            Observer<List<User?>?> { userList ->
                if (userList != null) {
                    for (user in userList) {
                        Log.i("ID : ", "" + user?.userId)
                        Log.i("Name : ", "" + user?.userName)
                        Log.i("Age : ", "" + user?.userAge)
                        Log.i("Place : ", "" + user?.userPlace)
                    }
                }
            })
    }

    private fun observeGetUserRootFromNetwork(userProfileViewModel: UserProfileViewModel) {
        userProfileViewModel.getUserRootModelFromNetworkObservable()
            ?.observe(viewLifecycleOwner,
                Observer<UserModelRoot> { t ->
                    val userModelRoot: UserModel? = t?.data
                    Log.i("-----> ", "" + userModelRoot!!.id)
                    Log.i("-----> ", "" + userModelRoot.firstName)
                    Log.i("-----> ", "" + userModelRoot.lastName)
                    Log.i("-----> ", "" + userModelRoot.email)
                    Log.i("-----> ", "" + userModelRoot.avatar)
                })
    }

    private fun observeGetUserRootFromNetworkSupportCache(userProfileViewModel: UserProfileViewModel) {
        userProfileViewModel.getUserRootModelFromNetworkObservable()
            ?.observe(viewLifecycleOwner,
                Observer<UserModelRoot> { t ->
                    val userModelRoot: UserModel? = t?.data
                    Log.i(TAG, "" + userModelRoot!!.id)
                    Log.i(TAG, "" + userModelRoot.firstName)
                    Log.i(TAG, "" + userModelRoot.lastName)
                    Log.i(TAG, "" + userModelRoot.email)
                    Log.i(TAG, "" + userModelRoot.avatar)
                })
    }

    private fun observeGetUserModelListRootFromNetwork(userProfileViewModel: UserProfileViewModel) {
        userProfileViewModel.fetchUserListFromNetworkObservable()
            ?.observe(viewLifecycleOwner,
                Observer<UserListModelRoot> { t ->
                    val userModelList = t?.userModelList
                    for (user in userModelList!!) {
                        Log.i("-----> ", "" + user.id)
                        Log.i("-----> ", "" + user.firstName)
                        Log.i("-----> ", "" + user.lastName)
                        Log.i("-----> ", "" + user.email)
                        Log.i("-----> ", "" + user.avatar)
                    }
                })
    }

    private fun observeUserModelPostResponseFromNetwork(userProfileViewModel: UserProfileViewModel) {
        userProfileViewModel.fetchUserModelPostRequestFromNetworkObservable()
            ?.observe(viewLifecycleOwner,
                Observer<UserModelForPostResponse> { user ->
                    Log.i("-----> ", "" + user?.userName)
                    Log.i("-----> ", "" + user?.userJob)
                    Log.i("-----> ", "" + user?.userId)
                    Log.i("-----> ", "" + user?.userCreatedAt)
                })
    }

    private fun observePersistenceUserFromRoom(userProfileViewModel: UserProfileViewModel) {
        userProfileViewModel.fetchPersistenceUserWithRoomObservable()
            ?.observe(viewLifecycleOwner, Observer<PersistenceUser> { user: PersistenceUser? ->
                Log.i("-----> P", "" + user?.id)
                Log.i("-----> P", "" + user?.firstName)
                Log.i("-----> P", "" + user?.lastName)
                Log.i("-----> P", "" + user?.avatar)
            })
    }

    private fun observerRetrievePersistenceUserRootInRoom(userProfileViewModel: UserProfileViewModel) {
        userProfileViewModel.fetchPersistenceUserRootWithRoomObservable()
            ?.observe(
                viewLifecycleOwner,
                Observer<PersistenceUserRoot> { t: PersistenceUserRoot? ->
                    val persistenceUserRoot = t?.data
                    Log.i("-----> PR", "" + persistenceUserRoot?.id)
                    Log.i("-----> PR", "" + persistenceUserRoot?.firstName)
                    Log.i("-----> PR", "" + persistenceUserRoot?.lastName)
                    Log.i("-----> PR", "" + persistenceUserRoot?.email)
                    Log.i("-----> PR", "" + persistenceUserRoot?.avatar)
                })
    }
}


