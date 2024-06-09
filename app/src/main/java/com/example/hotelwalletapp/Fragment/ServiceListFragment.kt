package com.example.hotelwalletapp.Fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.hotelwalletapp.Adapter.ListAdapter
import com.example.hotelwalletapp.Adapter.OnServiceClickListener
import com.example.hotelwalletapp.Api.RetrofitService
import com.example.hotelwalletapp.R
import com.example.hotelwalletapp.Repository.ServicesRepository
import com.example.hotelwalletapp.databinding.RecyclerViewMenuBinding
import com.example.hotelwalletapp.model.Service
import com.example.hotelwalletapp.viewmodel.ServicesViewModel
import com.example.hotelwalletapp.viewmodel.ServicesviewModelFactory
import com.example.hotelwalletapp.viewmodel.SharedViewModel
import com.google.android.material.snackbar.Snackbar

class ServiceListFragment : Fragment()  , OnServiceClickListener {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val LIST_VIEW = "LiST_VIEW"
    private val GRID_VIEW = "GRID_VIEW"
    var currentview = LIST_VIEW
    private lateinit var binding: RecyclerViewMenuBinding
    lateinit var viewModel: ServicesViewModel
    private val retrofitService = RetrofitService.getInstance()
    val adapter = ListAdapter(this)

    override fun onServiceClicked(service: Service) {
        sharedViewModel.addService(service)
        val snackbar = Snackbar.make(binding.root,  "the service , ${service.Libelle} , has been successfully added to your list  ", Toast.LENGTH_SHORT)
        val view = snackbar.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.BOTTOM
        view.layoutParams = params
        view.setBackgroundColor(ContextCompat.getColor(requireContext(), androidx.appcompat.R.color.material_deep_teal_200))
        snackbar.show()
    }


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = arguments?.getString("type")
        binding = RecyclerViewMenuBinding.inflate(layoutInflater)
        val imageslider = binding.imageslider
        val imagelist = ArrayList<SlideModel>()
        imagelist.add(
            SlideModel(
                "https://cdn.sortiraparis.com/images/80/100789/834099-too-restaurant-too-hotel-paris-photos-menu-ribs.jpg",
                "Restaurant Services"
            )
        )
        imagelist.add(
            SlideModel(
                "https://cdn.aarp.net/content/dam/aarp/entertainment/beauty-and-style/2018/08/1140-candlelight.imgcache.rev.web.900.517.jpg",
                "Spa Services"
            )
        )
        imagelist.add(
            SlideModel(
                "https://www.mashed.com/img/gallery/the-worst-things-to-order-from-hotel-room-service/intro-1551812619.jpg",
                "Room Services"
            )
        )
        imageslider?.setImageList(imagelist, ScaleTypes.FIT)

        viewModel = ViewModelProvider(this, ServicesviewModelFactory(ServicesRepository(retrofitService))).get(
            ServicesViewModel::class.java)
        binding.recyclerview.adapter = adapter
     /*   viewModel.serviceList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setDataList(it)
        })*/
        viewModel.serviceList.observe(viewLifecycleOwner, Observer {
            val filteredList = it.filter { service ->
                when (type) {
                    "Room" -> service.Typeserv == "Chambre"
                    "Restaurant" -> service.Typeserv == "Restaurant"
                    "SPA" -> service.Typeserv == "SPA"
                    else -> true
                }
            }
            adapter.setDataList(filteredList)
        })


        var currentSortOrder = SortOrder.DESCENDING

        binding.sortButton.setOnClickListener {
            currentSortOrder = if (currentSortOrder == SortOrder.DESCENDING) {
                SortOrder.ASCENDING
            } else {
                SortOrder.DESCENDING
            }

            adapter.sortListByPrice(currentSortOrder == SortOrder.ASCENDING)
        }


        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
        })
        viewModel.getAllServices()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchView = binding.search


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })


        listView()
        binding.gridButton.setOnClickListener {
            if (currentview == LIST_VIEW) {
                binding.gridButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_grid_view
                    )
                )
                gridView()
            } else {
                binding.gridButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_format_list_bulleted
                    )
                )
                listView()
            }
        }
    }

    companion object {
        fun newInstance(type: String?): ServiceListFragment {
            val args = Bundle()
            args.putString("type", type)
            val fragment = ServiceListFragment()
            fragment.arguments = args
            return fragment
        }
    }





    private fun listView() {
        currentview = LIST_VIEW
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter
    }

    private fun gridView() {
        currentview = GRID_VIEW
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerview.adapter = adapter
    }

    enum class SortOrder {
        ASCENDING,
        DESCENDING
    }


}

