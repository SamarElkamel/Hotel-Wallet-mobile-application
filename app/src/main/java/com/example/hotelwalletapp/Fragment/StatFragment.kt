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
import com.example.hotelwalletapp.databinding.HistoryRecyclerviewBinding
import com.example.hotelwalletapp.databinding.StatBinding
import com.example.hotelwalletapp.model.Command
import com.example.hotelwalletapp.viewmodel.ClientViewModel
import com.example.hotelwalletapp.viewmodel.ClientViewModelFactory
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class StatFragment : Fragment() {
    private lateinit var binding: StatBinding
    private lateinit var viewModel: ClientViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatBinding.inflate(inflater, container, false)
        val barChart: BarChart = binding.barChart
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

        val barChart: BarChart = binding.barChart
        viewModel.client.observe(viewLifecycleOwner) { client ->
            client?.let {
                val historyData = client.history
                generateBarChart(historyData, barChart)
            }
        }
    }

    private fun generateBarChart(historyData: List<Command>, barChart: BarChart) {
        val groupedByDate = historyData.groupBy { it.Date }
        val aggregatedDataByDate = groupedByDate.map { (date, commands) ->
            val totalRevenue = commands.sumBy { it.totalPrice.toInt() }
            date to totalRevenue
        }
        val entries = aggregatedDataByDate.mapIndexed { index, (date, revenue) ->
            BarEntry(index.toFloat(), revenue.toFloat())
        }

        val dataSet = BarDataSet(entries, "Expences by Date")
        dataSet.color = Color.BLUE
        val barData = BarData(dataSet)
        barChart.data = barData
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(aggregatedDataByDate.map { it.first })
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.granularity = 1f
        barChart.xAxis.labelCount = aggregatedDataByDate.size
        barChart.description.isEnabled = false
    }

}
