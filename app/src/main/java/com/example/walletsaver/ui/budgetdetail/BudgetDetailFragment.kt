package com.example.walletsaver.ui.budgetdetail

import android.graphics.Color
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs

import com.example.walletsaver.R
import com.example.walletsaver.database.BudgetDatabase
import com.example.walletsaver.databinding.FragmentBudgetDetailBinding
import com.example.walletsaver.ui.home.HomeFragmentDirections
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_add_budget.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil

class BudgetDetailFragment : Fragment() {

    private lateinit var lineChart: LineChart

    private lateinit var viewModel: BudgetDetailViewModel
    private lateinit var binding: FragmentBudgetDetailBinding
    private lateinit var viewModelFactory: BudgetDetailViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_budget_detail, container, false)

        lineChart = binding.lineChart

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = BudgetDatabase.getInstance(application).budgetDatabaseDao

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
            (activity as AppCompatActivity).supportActionBar?.title = viewModel.budget.value?.category
        })

        lineChart.setDragEnabled(true)
        lineChart.setScaleEnabled(false)

        lineChart.getAxisRight().setEnabled(false)
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM)


        val yValues = ArrayList<Entry>()

        yValues.add(Entry(0f, 60f))
        yValues.add(Entry(1f, 50f))
        yValues.add(Entry(2f, 70f))
        yValues.add(Entry(3f, 30f))

        val set1 = LineDataSet(yValues, "Gastos")

        set1.setFillColor(Color.RED)
        set1.setDrawFilled(true)

        set1.setFillAlpha(85)
        set1.setColor(Color.RED)
        set1.setLineWidth(2f)
        set1.setValueTextSize(0f)
        set1.setCircleColor(Color.RED)
        set1.enableDashedLine(3f, 2f, 0f)



        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)

        val data = LineData(dataSets)

        lineChart.setData(data)

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
