package com.example.hotelwalletapp.Fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hotelwalletapp.Api.APIService
import com.example.hotelwalletapp.Repository.ClientRepository
import com.example.hotelwalletapp.databinding.PiestatBinding
import com.example.hotelwalletapp.databinding.StatBinding
import com.example.hotelwalletapp.model.Command
import com.example.hotelwalletapp.viewmodel.ClientViewModel
import com.example.hotelwalletapp.viewmodel.ClientViewModelFactory
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class StatByTypeFragment : Fragment() {
    private lateinit var binding: PiestatBinding
    private lateinit var viewModel: ClientViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PiestatBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            ClientViewModelFactory(
                ClientRepository(APIService.getUpdatedService()),
                requireActivity().application
            )
        ).get(ClientViewModel::class.java)

        val pieChart: PieChart = binding.barChart
        viewModel.client.observe(viewLifecycleOwner) { client ->
            client?.let {
                val historyData = client.history
                generatePieChart(historyData, pieChart)
            }
        }
    }

    private fun generatePieChart(historyData: List<Command>, pieChart: PieChart) {
        val groupedByDate = historyData.groupBy { it.type }
        val aggregatedDataByDate = groupedByDate.map { (date, commands) ->
            val totalRevenue = commands.sumBy { it.totalPrice.toInt() }
            date to totalRevenue
        }
        val entries = aggregatedDataByDate.map { (date, revenue) ->
            PieEntry(revenue.toFloat(), date)
        }

        val dataSet = PieDataSet(entries, "Expenses by Type")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = true
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.animateXY(1000, 1000)
        pieChart.invalidate()
    }
}

