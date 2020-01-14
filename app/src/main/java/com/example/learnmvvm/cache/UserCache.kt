package com.example.learnmvvm.cache

import androidx.lifecycle.LiveData
import com.example.learnmvvm.network.UserModelRoot

class UserCache {
    companion object {
        var userCacheHashMap = HashMap<Int, LiveData<UserModelRoot>>()
    }
}