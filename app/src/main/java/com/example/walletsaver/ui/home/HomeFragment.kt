package com.example.walletsaver.ui.home

import android.os.Bundle
import android.view.*
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.provider.CalendarContract
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.walletsaver.R
import com.example.walletsaver.database.BudgetDatabase
import com.example.walletsaver.databinding.FragmentHomeBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.animation.Easing.EaseInOutCubic
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var binding: FragmentHomeBinding

    private val STORAGE_REQUEST_CODE = 101
    private val TAG = "PermissionDemo"

    var esVisible = true

    private lateinit var pieChart: PieChart


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val dataSource = BudgetDatabase.getInstance(application).budgetDatabaseDao
        viewModelFactory = HomeViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.budgetClicked.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                requireView().findNavController().navigate(HomeFragmentDirections
                    .actionNavigationHomeToBudgetDetailFragment(it))
                viewModel.onBudgetClickedCompleted()
            }
        })
        
        val adapter = BudgetAdapter(BudgetClickListener {
            viewModel.onBudgetClicked(it)
        })

        binding.budgetList.adapter = adapter

        viewModel.budgets.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.rowsCount.observe(viewLifecycleOwner, Observer { count ->
            if (count == 0) {
                if (esVisible) {
                    binding.linearLayout.visibility = View.INVISIBLE
                    binding.emptyScreen.visibility = View.VISIBLE
                    esVisible = false
                } else {
                    binding.linearLayout.visibility = View.VISIBLE
                    binding.emptyScreen.visibility = View.INVISIBLE
                    esVisible = true
                }
            }
        })
//
//        var budgetFloat = 0.0f
//
//        viewModel.sumOfBudgets.observe(viewLifecycleOwner, Observer { budget ->
//            budgetFloat = budget.toFloat()
//        })
//
//        pieChart = binding.pieChart
//
//        pieChart.setUsePercentValues(true)
//        pieChart.getDescription().setEnabled(false)
//        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
//
//        pieChart.setDragDecelerationFrictionCoef(0.95f)
//
//        pieChart.setDrawHoleEnabled(true)
//        pieChart.setHoleColor(Color.WHITE)
//        pieChart.setTransparentCircleRadius(61f)
//
//        pieChart.animateY(1000, Easing.EaseInOutCubic)
//
//        val yValue = ArrayList<PieEntry>()
//
//        yValue.add(PieEntry(34f, "Ingresos"))
//        yValue.add(PieEntry(budgetFloat, "Presupuestos"))
//        yValue.add(PieEntry(48f, "Gastos"))
//
//        val dataSet = PieDataSet(yValue, "Countries")
//        dataSet.setSliceSpace(3f)
//        dataSet.setSelectionShift(5f)
//        dataSet.setColors(Color.BLUE)
//
//        val data = PieData(dataSet)
//        data.setValueTextSize(10f)
//        data.setValueTextColor(Color.YELLOW)
//
//        pieChart.setData(data)




//        binding.buttonOk.setOnClickListener {
//            viewModel.sumOfBudgets.observe(viewLifecycleOwner, Observer { budget ->
//                val budgetString = java.text.NumberFormat.getIntegerInstance().format(budget)
//                binding.textView18.text = budgetString
//            })
//
//            viewModel.sumOfIncomes.observe(viewLifecycleOwner, Observer { income ->
//                binding.textView16.text = income.toString()
//            })
//
//            viewModel.sumOfExpenses.observe(viewLifecycleOwner, Observer { expense ->
//                binding.textView17.text = expense.toString()
//            })
//        }

        binding.fab.setOnClickListener {
            setupPermissions()
        }
    }

    private fun setupPermissions() {

        if (getContext()?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE) }
                    != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getContext() as Activity,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(activity, "Need", Toast.LENGTH_SHORT).show()
            } else {
                ActivityCompat.requestPermissions(getContext() as Activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_REQUEST_CODE)
            }
        } else {
            findNavController().navigate(R.id.action_navigation_home_to_tab_holder_fragment)
        }
    }

    // Receive the permissions request result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            STORAGE_REQUEST_CODE -> {

                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    // Do the task now
                    findNavController().navigate(R.id.action_navigation_home_to_tab_holder_fragment)
                }else{
                    Toast.makeText(activity, "Permissions denied.", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.settings_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            findNavController().navigate(R.id.action_navigation_home_to_settings_fragment)
        }
        return super.onOptionsItemSelected(item)
    }
}
