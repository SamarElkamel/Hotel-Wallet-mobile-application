package com.example.hotelwalletapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.hotelwalletapp.Adapter.SliderPagerAdapter
import com.example.hotelwalletapp.databinding.HistoryRecyclerviewBinding
import com.example.hotelwalletapp.databinding.StatViewpagerBinding
import kotlinx.android.synthetic.main.navhostfragment.*

class SliderFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var binding:StatViewpagerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StatViewpagerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewPager
        val adapter = SliderPagerAdapter(parentFragmentManager)
        viewPager.adapter = adapter
    }
}
