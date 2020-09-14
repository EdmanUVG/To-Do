package com.example.walletsaver.ui.taskdetail

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.walletsaver.R
import com.example.walletsaver.database.WalletDatabase
import com.example.walletsaver.databinding.FragmentBudgetDetailBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


class BudgetDetailFragment : Fragment() {

    private lateinit var lineChart: LineChart
    private lateinit var barChart: BarChart

    private lateinit var viewModel: BudgetDetailViewModel
    private lateinit var binding: FragmentBudgetDetailBinding
    private lateinit var viewModelFactory: BudgetDetailViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_budget_detail, container, false)

        lineChart = binding.lineChart
        barChart = binding.barChart

        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = WalletDatabase.getInstance(application).taskDatabaseDao

        val budgetViewFragmentArgs by navArgs<BudgetDetailFragmentArgs>()

        viewModelFactory = BudgetDetailViewModelFactory(dataSource, budgetViewFragmentArgs.budgetId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BudgetDetailViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.budgetClicked.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                requireView().findNavController().navigate(
                    BudgetDetailFragmentDirections
                    .actionBudgetDetailFragmentToBudgetEditFragment(it))
                viewModel.onBudgetClickedCompleted()
            }
        })

        binding.imageEdit.setOnClickListener {
            viewModel.onBudgetClicked(binding.textId.text.toString().toLong())
        }

        viewModel.budget.observe(viewLifecycleOwner, Observer {
            (activity as AppCompatActivity).supportActionBar?.title = viewModel.budget.value?.tag
        })

        // Line Chart
        lineChart.setGridBackgroundColor(128)
        lineChart.setBorderColor(255)
        lineChart.getAxisRight().setEnabled(false)
        lineChart.getAxisRight().setDrawLabels(false)
        lineChart.getAxisLeft().setDrawLabels(true)
        lineChart.getLegend().setEnabled(false)
        lineChart.setPinchZoom(false)
        lineChart.getDescription().setEnabled(false)
        lineChart.setTouchEnabled(false)
        lineChart.setDoubleTapToZoomEnabled(false)
        lineChart.getXAxis().setEnabled(true)
        lineChart.setDrawGridBackground(true)

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM)
        lineChart.getXAxis().setDrawGridLines(false) //enable for grid line

        lineChart.invalidate()

        val yValues = ArrayList<Entry>()

        yValues.add(Entry(0f, 60f))
        yValues.add(Entry(1f, 50f))
        yValues.add(Entry(2f, 70f))
        yValues.add(Entry(3f, 30f))

        val set1 = LineDataSet(yValues, "Gastos")

        set1.setFillColor(Color.rgb(255, 69, 0))
        set1.setFillAlpha(55)  //85
        set1.setColor(Color.rgb(255, 69, 0))
        set1.setLineWidth(2f)
        set1.setValueTextSize(0f)
        set1.setCircleColor(Color.rgb(255, 69, 0))

        set1.setDrawFilled(true)
//        val fillGradient = ContextCompat.getDrawable(requireContext(), R.drawable.fade_red)
//        set1.fillDrawable = fillGradient

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)

        val data = LineData(dataSets)
        lineChart.setData(data)


        // Bar Chart
        val barDataSet = BarDataSet(getData(), "Inducesmile")
        barDataSet.setBarBorderWidth(0.9f)
        barDataSet.setBarBorderColor(Color.rgb(0, 94, 203))
        barDataSet.setColor(Color.rgb(91, 155, 213))
        barDataSet.setDrawValues(false)
        val barData = BarData(barDataSet)
        barData.setBarWidth(0.5f)       // 1f IS FULL WIDTH
        val xAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val months =
            arrayOf("Mar", "Apr", "May", "Jun")

        val formatter = IndexAxisValueFormatter(months)
        xAxis.granularity = 1f
        xAxis.valueFormatter = formatter

        barChart.setDrawValueAboveBar(false)
        barChart.setGridBackgroundColor(128)
        barChart.getAxisRight().setEnabled(false)
        barChart.getAxisRight().setDrawLabels(false)
        barChart.getAxisLeft().setDrawLabels(true)
        barChart.getLegend().setEnabled(false)
        barChart.setPinchZoom(false)
        barChart.getDescription().setEnabled(false)
        barChart.setTouchEnabled(false)
        barChart.setDoubleTapToZoomEnabled(false)
        barChart.getXAxis().setEnabled(true)
        barChart.setDrawGridBackground(true)

        barChart.getXAxis().setDrawGridLines(false) //enable for grid line

        barChart.data = barData
        barChart.setFitBars(true)
        barChart.animateXY(2500, 2500)
        barChart.invalidate()

    }

    private fun getData(): ArrayList<BarEntry> {
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 30f))
        entries.add(BarEntry(1f, 80f))
        entries.add(BarEntry(2f, 60f))
        entries.add(BarEntry(3f, 50f))
        return entries
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {

            viewModel.deleteBudget()
            activity?.onBackPressed()
            Toast.makeText(activity, "Removed", Toast.LENGTH_SHORT).show()

        }
        return super.onOptionsItemSelected(item)
    }
}
