package com.example.walletsaver.ui.home

import android.os.Bundle
import android.view.*
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.walletsaver.R
import com.example.walletsaver.database.WalletDatabase
import com.example.walletsaver.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_filter_layout.view.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var binding: FragmentHomeBinding

    private val STORAGE_REQUEST_CODE = 101
    private val TAG = "PermissionDemo"

    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    var esVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title =  getString(com.example.walletsaver.R.string.edman)

        initPrefs()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        setHasOptionsMenu(true)
        activity?.let { UIUtil.hideKeyboard(it) }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val dataSourceBudget = WalletDatabase.getInstance(application).taskDatabaseDao

        viewModelFactory = HomeViewModelFactory(dataSourceBudget)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.budgetClicked.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                requireView().findNavController().navigate(HomeFragmentDirections
                    .actionNavigationHomeToBudgetDetailFragment(it))
                viewModel.onBudgetClickedCompleted()
            }
        })
        
        val adapter = TaskAdapter(TaskClickListener {
            viewModel.onBudgetClicked(it)
        })

        val adapterCompleted = TaskCompletedAdapter(TaskClickListeners {
            viewModel.onBudgetClicked(it)
        })

        binding.taskList.adapter = adapter
        binding.taskCompletedList.adapter = adapterCompleted

        // Will display the list in the order selected
        viewModel.isSorted.observe(viewLifecycleOwner, Observer { isSorted ->
            if (isSorted == 0) {
                viewModel.tasks.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapter.submitList(it)
                    }
                })

                // Completed task adapter
                viewModel.completedTasks.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapterCompleted.submitList(it)
                    }
                })

            } else if(isSorted == 1) {
                viewModel.tasksByPriority.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapter.submitList(it)
                    }
                })

                // Completed task adapter
                viewModel.completedTasks.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapterCompleted.submitList(it)
                    }
                })
            } else if (isSorted == 2) {
                viewModel.taskByDate.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapter.submitList(it)
                    }
                })

                // Completed task adapter
                viewModel.completedTasks.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapterCompleted.submitList(it)
                    }
                })
            }
        })


        // Check if database is empty to display empty image background
        viewModel.rowsCount.observe(viewLifecycleOwner, Observer { count ->
            if (count == 0) {
                if (esVisible) {
                    binding.emptyScreen.visibility = View.VISIBLE
                    esVisible = false
                } else {
                    binding.emptyScreen.visibility = View.INVISIBLE
                    esVisible = true
                }
            }
        })

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
            val mBottomSheetFragment = BottomSheetAddTask()
            mBottomSheetFragment.show(requireActivity().supportFragmentManager, "MY_BOTTOM_SHEET")
        }
    }

    // Receive the permissions request result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            STORAGE_REQUEST_CODE -> {

                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    // Do the task now
                    val mBottomSheetFragment = BottomSheetAddTask()
                    mBottomSheetFragment.show(requireActivity().supportFragmentManager, "MY_BOTTOM_SHEET")
                }else{
                    Toast.makeText(activity, "Permissions denied.", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_nav_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            findNavController().navigate(R.id.action_navigation_home_to_settings_fragment)
        }

        if (item.itemId == R.id.action_filter) {
            showSortBottomSheetDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initPrefs() {
        preferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    private fun showSortBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_filter_layout, null)
        val dialog = context?.let { BottomSheetDialog(it) }

        dialog?.setContentView(view)


        // Set initial preferences
//        val filter = preferences.getString(getString(R.string.key_filter), getString(R.string.filter_custom))
//        if (filter.equals(getString(R.string.filter_custom))){
//            dialog?.findViewById<RadioGroup>(R.id.filterGroup)?.check(R.id.filterCustom)
//        } else if (filter.equals(getString(R.string.filter_priority))){
//            dialog?.findViewById<RadioGroup>(R.id.filterGroup)?.check(R.id.filterPriority)
//        } else{
//            dialog?.findViewById<RadioGroup>(R.id.filterGroup)?.check(R.id.filterDate)
//        }

        // Will display the list in the order selected
        viewModel.isSorted.observe(viewLifecycleOwner, Observer { isSorted ->
            if (isSorted == 0) {
                dialog?.findViewById<RadioGroup>(R.id.filterGroup)?.check(R.id.filterCustom)
            } else if (isSorted == 1) {
                dialog?.findViewById<RadioGroup>(R.id.filterGroup)?.check(R.id.filterPriority)
            } else {
                dialog?.findViewById<RadioGroup>(R.id.filterGroup)?.check(R.id.filterDate)
            }
        })


        view.filterCustom.setOnClickListener {
            viewModel.onNoPriorityClicked()
            dialog?.dismiss()
        }
        view.filterPriority.setOnClickListener {
            viewModel.onPriorityClicked()
            dialog?.dismiss()
        }
        view.filterDate.setOnClickListener {
            viewModel.onDateClicked()
            dialog?.dismiss()
        }
        
        dialog?.show()
    }
}
