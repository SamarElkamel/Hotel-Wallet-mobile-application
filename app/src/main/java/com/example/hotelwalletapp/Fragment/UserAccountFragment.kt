package com.example.hotelwalletapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Api.APIService
import com.example.hotelwalletapp.R
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.Token.AuthClient
import com.example.hotelwalletapp.databinding.UseraccountmainBinding
import com.example.hotelwalletapp.viewmodel.ClientViewModel
import com.example.hotelwalletapp.viewmodel.ClientViewModelFactory

class UserAccountFragment :  Fragment()  {

    private lateinit var ViewModel: ClientViewModel
    private lateinit var binding: UseraccountmainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UseraccountmainBinding.inflate(layoutInflater)
        ViewModel = ViewModelProvider(
            this,
            ClientViewModelFactory(
                ClientRepository(APIService.getUpdatedService()),
                requireActivity().application
            )
        ).get(ClientViewModel::class.java)

        val toolbar = view?.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar?.setNavigationIcon(R.drawable.ic_baseline_menu)
        val drawerLayout = view?.findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = binding.navView

        toolbar?.setNavigationOnClickListener {
            drawerLayout?.openDrawer(GravityCompat.START)
        }
        val mainfragment = ProfileFragment()
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, mainfragment)
                        .addToBackStack(null)
                        .commit()
                    navigationView.setCheckedItem(R.id.nav_home );
                    true
                }

                R.id.nav_profile -> {
                    val fragment = EditProfile()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                    navigationView.setCheckedItem(R.id.nav_profile );
                    true
                }
                R.id.nav_settings -> {
                    val fragment = ChangePasswordFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                    navigationView.setCheckedItem(R.id.nav_settings );
                    true
                }


                R.id.nav_history-> {
                    val fragment = HistoryFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                    navigationView.setCheckedItem(R.id.nav_settings );
                    true
                }


                R.id.nav_claim -> {
                    val fragment = ClaimFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                    navigationView.setCheckedItem(R.id.nav_claim );
                    true
                }

                R.id.nav_logout -> {
                    AuthClient.getInstance().logout()
                    val fragment = LoginFragment()
                    val transaction =requireActivity().supportFragmentManager.beginTransaction()
                    val flFragment = requireActivity().findViewById<FrameLayout>(R.id.flFragment)
                    if (flFragment != null) {
                         transaction.replace(R.id.flFragment, fragment)
                         transaction.addToBackStack(null)
                    } else {
                        transaction.replace(R.id.maincont, fragment)
                        transaction.addToBackStack(null)
                    }

                    transaction.commit()
                    navigationView.setCheckedItem(R.id.nav_logout);
                    true
                }




                else -> false
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainFragment = ProfileFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, mainFragment)
            .commit()
    }

    companion object {
        fun newInstance(id: String? , num: String?, type: String?,credit:Double): UserAccountFragment {
            val args = Bundle()
            args.putString("id", id)
            args.putString("num", num)
            args.putString("type", type)
            args.putDouble("credit", credit)
            val fragment = UserAccountFragment()
            fragment.arguments = args
            return fragment
        }
    }
}



