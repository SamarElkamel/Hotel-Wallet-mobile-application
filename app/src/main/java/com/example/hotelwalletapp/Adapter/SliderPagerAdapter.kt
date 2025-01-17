package com.example.hotelwalletapp.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.hotelwalletapp.Fragment.HistoryFragment
import com.example.hotelwalletapp.Fragment.StatByTypeFragment
import com.example.hotelwalletapp.Fragment.StatFragment

class SliderPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> StatByTypeFragment()
            1 -> StatFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getCount(): Int {
        return 2
    }
}
