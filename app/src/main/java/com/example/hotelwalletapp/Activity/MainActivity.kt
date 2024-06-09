package com.example.hotelwalletapp.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Fragment.LoginFragment
import com.example.hotelwalletapp.Fragment.MyCardFragment
import com.example.hotelwalletapp.Fragment.ServiceListFragment
import com.example.hotelwalletapp.Fragment.UserAccountFragment
import com.example.hotelwalletapp.R
import com.example.hotelwalletapp.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(
            SharedViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val type = intent.getStringExtra("type")
        val id = intent.getStringExtra("id")
        val num = intent.getStringExtra("num")
        val firstFragment= ServiceListFragment.newInstance(type)
        setCurrentFragment(firstFragment)
        val secondFragment = MyCardFragment.newInstance(id,num,type)
        val thirdFragment = LoginFragment()

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(firstFragment)
                R.id.list -> setCurrentFragment(secondFragment)
                R.id.user -> setCurrentFragment(thirdFragment)
                R.id.qrcode->Qrcode ()
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    private fun Qrcode ()
    {
        val intent = Intent(this, QrCodeActivity::class.java)
        startActivity(intent)
    }


    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (fragment is LoginFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

}


