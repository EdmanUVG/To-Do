package com.example.walletsaver.ui.home

import android.os.Bundle
import android.view.*
import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.walletsaver.R
import com.example.walletsaver.database.WalletDatabase
import com.example.walletsaver.databinding.FragmentHomeBinding
import com.example.walletsaver.ui.budgetedit.BottomSheetFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_add_task.*
import kotlinx.android.synthetic.main.bottom_sheet_add_task.view.*
import kotlinx.android.synthetic.main.bottom_sheet_filter_layout.view.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var binding: FragmentHomeBinding

    private val STORAGE_REQUEST_CODE = 101
    private val TAG = "PermissionDemo"

    var formater = SimpleDateFormat("MMM dd", Locale.US)

    var esVisible = true
    private var dueDate = ""


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
        
        val adapter = BudgetAdapter(BudgetClickListener {
            viewModel.onBudgetClicked(it)
        })

        binding.budgetList.adapter = adapter

        // Will display the list in the order selected
        viewModel.isSorted.observe(viewLifecycleOwner, Observer { isSorted ->
            if (isSorted) {
                viewModel.tasksByPriority.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapter.submitList(it)
                    }
                })
            } else {
                viewModel.tasks.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapter.submitList(it)
                    }
                })
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
        if (item.itemId == R.id.action_share) {
            val mBottomSheetFragment = BottomSheetAddTask()
            mBottomSheetFragment.show(requireActivity().supportFragmentManager, "MY_BOTTOM_SHEET")
        }

        if (item.itemId == R.id.action_filter) {
            showBottomSheetDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_filter_layout, null)
        val dialog = context?.let { BottomSheetDialog(it) }

        dialog?.setContentView(view)

        view.viewPriority.setOnClickListener {
            viewModel.onPriorityClicked()
            dialog?.dismiss()
        }
        view.viewNone.setOnClickListener {
            viewModel.onNoPriorityClicked()
            dialog?.dismiss()
        }
        
        dialog?.show()
    }
}
