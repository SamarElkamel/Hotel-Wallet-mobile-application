package com.example.hotelwalletapp.Fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hotelwalletapp.Adapter.MyCardAdapter
import com.example.hotelwalletapp.Api.APIService
import com.example.hotelwalletapp.Api.RetrofitService
import com.example.hotelwalletapp.R
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.Repository.CommandeRepository
import com.example.hotelwalletapp.Repository.ServicesRepository
import com.example.hotelwalletapp.databinding.MylistrecyclerviewBinding
import com.example.hotelwalletapp.model.Client
import com.example.hotelwalletapp.model.Command
import com.example.hotelwalletapp.model.Service
import com.example.hotelwalletapp.viewmodel.*
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class MyCardFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var commandeViewModel: CommandeViewModel
    private lateinit var binding: MylistrecyclerviewBinding
    private lateinit var myCardAdapter: MyCardAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val totalPrice = sharedViewModel.getTotalPrice()
        binding = MylistrecyclerviewBinding.inflate(layoutInflater)
        myCardAdapter = MyCardAdapter(sharedViewModel.myServiceList.value ?: emptyList(),sharedViewModel)
        //val services = sharedViewModel.myServiceList.value ?: emptyList()
        val services =sharedViewModel.myCommandeServiceList.value ?: mutableListOf()
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerview.adapter = myCardAdapter
        commandeViewModel = ViewModelProvider(
            this,
            CommandeViewModelFactory(
                CommandeRepository(APIService.getService()),
                requireActivity().application
            )
        ).get(CommandeViewModel::class.java)

        sharedViewModel.myServiceList.observe(viewLifecycleOwner, { serviceList ->
            myCardAdapter.updateData(serviceList)
            myCardAdapter.notifyDataSetChanged()
            binding.totalprx.text = "Total Price :" + String.format(" %.2f", sharedViewModel.getTotalPrice())
        })


        binding.button1.setOnClickListener {
            if (totalPrice == 0.0) {
                val snackbar = Snackbar.make(binding.root, "You must add services first", Snackbar.LENGTH_SHORT)
                val view = snackbar.view
                val params = view.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.CENTER
                view.layoutParams = params
                view.setBackgroundColor(ContextCompat.getColor(requireContext(), androidx.appcompat.R.color.error_color_material_dark))
                snackbar.show()
            } else {
                sharedViewModel.hasClickedButton = true
                val num = arguments?.getString("num") ?: ""
                val type = arguments?.getString("type") ?: ""
                val id =arguments?.getString("id") ?: ""
                openPayementFragment(id,num,type)


            }
        }
        return binding.root
    }




    companion object {
        fun newInstance(id:String?,num:String?,type: String?): MyCardFragment {
            val args = Bundle()
            args.putString("id", id)
            args.putString("num", num)
            args.putString("type", type)
            val fragment = MyCardFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private fun openPayementFragment(id: String?, num: String?, type: String?) {
        sharedViewModel.hasClickedButton = true
        val secondFragment = PayementFragment.newInstance(id, num, type)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, secondFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}






