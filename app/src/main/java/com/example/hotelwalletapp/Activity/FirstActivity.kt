package com.example.hotelwalletapp.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelwalletapp.Fragment.LoginFragment
import com.example.hotelwalletapp.R

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.firstactivity)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = LoginFragment()
        fragmentTransaction.replace(R.id.maincont, fragment)
        fragmentTransaction.commit()
        fragmentTransaction.addToBackStack(null)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.maincont)
        if (fragment is LoginFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}


