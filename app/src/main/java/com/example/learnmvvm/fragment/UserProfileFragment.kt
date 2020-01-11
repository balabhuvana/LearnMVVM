package com.example.learnmvvm.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.learnmvvm.R
import com.example.learnmvvm.network.UserModel
import com.example.learnmvvm.network.UserModelRoot
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userProfileViewModel =
            ViewModelProviders.of(this@UserProfileFragment)[UserProfileViewModel::class.java]


        btnInsert.setOnClickListener {
            val user = User()
            user.userName = "Anand Kumar"
            user.userAge = 31
            user.userPlace = "Salem"
            GlobalScope.launch {
                userProfileViewModel.insertUserRecord(user)
            }
        }

        btnSelectAllUser.setOnClickListener {
            userProfileViewModel.selectUserList()
            observeAllUserData(userProfileViewModel)
        }

        btnSelectSpecificUser.setOnClickListener {
            userProfileViewModel.selectSpecificUser(7)
            observeSpecificUser(userProfileViewModel)
        }

        btnDeleteSpecificUser.setOnClickListener {
            GlobalScope.launch {
                userProfileViewModel.deleteSpecificUser(7)
            }
        }

        btnDeleteAllUser.setOnClickListener {
            /*GlobalScope.launch {
                userProfileViewModel.deleteAllUser()
            }*/
            userProfileViewModel.selectUserFromNetwork()
            observeGetUserRootFromNetwork(userProfileViewModel)

        }

    }

    private fun observeSpecificUser(userProfileViewModel: UserProfileViewModel) {
        userProfileViewModel.getUserObservable()
            ?.observe(viewLifecycleOwner, object : Observer<User> {
                override fun onChanged(user: User?) {
                    Log.i("ID : ", "" + user?.userId)
                    Log.i("Name : ", "" + user?.userName)
                    Log.i("Age : ", "" + user?.userAge)
                    Log.i("Place : ", "" + user?.userPlace)
                }
            })
    }

    private fun observeAllUserData(userProfileViewModel: UserProfileViewModel) {
        userProfileViewModel.getUserListObservable()?.observe(
            viewLifecycleOwner,
            object : Observer<List<User?>?> {
                override fun onChanged(@Nullable userList: List<User?>?) {
                    if (userList != null) {
                        for (user in userList) {
                            Log.i("ID : ", "" + user?.userId)
                            Log.i("Name : ", "" + user?.userName)
                            Log.i("Age : ", "" + user?.userAge)
                            Log.i("Place : ", "" + user?.userPlace)
                        }
                    }
                }
            })
    }

    private fun observeGetUserRootFromNetwork(userProfileViewModel: UserProfileViewModel) {
        userProfileViewModel.getUserRootModelFromNetworkObservable()
            ?.observe(viewLifecycleOwner, object : Observer<UserModelRoot> {
                override fun onChanged(t: UserModelRoot?) {
                    val userModelRoot: UserModel? = t?.data
                    Log.i("-----> ", "" + userModelRoot!!.id)
                    Log.i("-----> ", "" + userModelRoot.firstName)
                    Log.i("-----> ", "" + userModelRoot.lastName)
                    Log.i("-----> ", "" + userModelRoot.email)
                    Log.i("-----> ", "" + userModelRoot.avatar)
                }
            })
    }
}


