package com.example.walletsaver.ui.overview

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.walletsaver.R
import com.example.walletsaver.database.WalletDatabase
import com.example.walletsaver.databinding.FragmentOverviewBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class OverviewFragment : Fragment() {

    private lateinit var viewModel: OverviewViewModel
    private lateinit var viewModelFactory: OverviewViewModelFactory


    private lateinit var lineChart: LineChart
    private lateinit var barChart: BarChart
    private lateinit var binding: FragmentOverviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)

        lineChart = binding.lineChart
        barChart = binding.barChart

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val dataSourceBudget = WalletDatabase.getInstance(application).taskDatabaseDao

        viewModelFactory = OverviewViewModelFactory(dataSourceBudget)
        viewModel = ViewModelProvider(this, viewModelFactory).get(OverviewViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.completedTasksCount.observe(viewLifecycleOwner, Observer { count ->
            binding.textCompletedCount.text = count.toString()
        })

        // LINE CHART
        lineChart.setGridBackgroundColor(58)
        lineChart.setBorderColor(255)
        lineChart.axisRight.isEnabled = false
        lineChart.axisRight.setDrawLabels(false)
        lineChart.axisLeft.setDrawLabels(true)
        lineChart.legend.setEnabled(false)
        lineChart.setPinchZoom(false)
        lineChart.description.setEnabled(false)
        lineChart.setTouchEnabled(false)
        lineChart.isDoubleTapToZoomEnabled = false
        lineChart.xAxis.isEnabled = true
        lineChart.setDrawGridBackground(true)

        lineChart.
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        lineChart.xAxis.setDrawGridLines(false) // enable for grid line
        lineChart.axisLeft.enableGridDashedLine(10f, 10f, 0f)

        // xAxis = vertical  --  axisLeft = horizontal
        lineChart.axisLeft.axisMinimum = 0F

        lineChart.invalidate()

        val yValues = ArrayList<Entry>()

        yValues.add(Entry(0f, 60f))
        yValues.add(Entry(1f, 50f))
        yValues.add(Entry(2f, 70f))
        yValues.add(Entry(3f, 30f))

        val set1 = LineDataSet(yValues, "Gastos")
        set1.fillColor = Color.rgb(255, 69, 0)
        set1.fillAlpha = 55
        set1.color = Color.rgb(255, 69, 0)
        set1.lineWidth = 2f
        set1.valueTextSize = 0f
        set1.setCircleColor(Color.rgb(255, 69, 0))
        set1.setDrawFilled(true)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)

        val data = LineData(dataSets)
        lineChart.data = data

        // BAR CHART
        val barDataSet = BarDataSet(getData(), "Inducesmile")
        barDataSet.barBorderWidth = 0.6f
        barDataSet.barBorderColor = Color.rgb(0, 94, 203)
        barDataSet.color = Color.rgb(91, 155, 213)
        barDataSet.setDrawValues(false)
        val barData = BarData(barDataSet)
        barData.barWidth = 0.4f

        barChart.setDrawValueAboveBar(false)
        barChart.setGridBackgroundColor(58)
        barChart.axisRight.isEnabled = false
        barChart.axisRight.setDrawLabels(false)
        barChart.axisLeft.setDrawLabels(true)
        barChart.axisLeft.isEnabled = true
        barChart.legend.isEnabled = false
        barChart.setPinchZoom(false)
        barChart.description.isEnabled = false
        barChart.setTouchEnabled(false)
        barChart.isDoubleTapToZoomEnabled = false
        barChart.setDrawGridBackground(true)

        barChart.xAxis.setDrawGridLines(false)
        barChart.axisLeft.enableGridDashedLine(10f, 10f, 0f)

        // xAxis = vertical  --  axisLeft = horizontal
        barChart.axisLeft.axisMinimum = 0F


        val xAxis = barChart.xAxis

        barChart.
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val months =
            arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

        val formatter = IndexAxisValueFormatter(months)
        xAxis.granularity = 1f
        xAxis.valueFormatter = formatter

        barChart.data = barData
        barChart.setFitBars(true)
        barChart.animateXY(1000, 1000)
        barChart.invalidate()
    }

    private fun getData(): ArrayList<BarEntry> {

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 0f))
        entries.add(BarEntry(1f, 0f))
        entries.add(BarEntry(2f, 4f))
        entries.add(BarEntry(3f, 2f))
        entries.add(BarEntry(4f, 6f))
        entries.add(BarEntry(5f, 0f))
        entries.add(BarEntry(6f, 0f))
        return entries
    }
}










