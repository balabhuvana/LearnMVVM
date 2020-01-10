package com.example.learnmvvm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.learnmvvm.R
import com.example.learnmvvm.fragment.UserProfileFragment

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        supportFragmentManager.beginTransaction()
            .replace(R.id.userProfileFrameLayout, UserProfileFragment()).commit()

    }
}
