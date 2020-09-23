package com.example.walletsaver.ui.taskdetail

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.walletsaver.R
import com.example.walletsaver.database.WalletDatabase
import com.example.walletsaver.databinding.FragmentTaskDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.bottom_sheet_priority.view.*
import kotlinx.android.synthetic.main.fragment_task_detail.*
import java.text.SimpleDateFormat
import java.util.*


class TaskDetailFragment : Fragment() {


    private lateinit var viewModel: TaskDetailViewModel
    private lateinit var viewModelFactory: TaskDetailViewModelFactory
    private lateinit var binding: FragmentTaskDetailBinding

    var formater = SimpleDateFormat("MMM dd", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {

        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear)

        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_detail, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = WalletDatabase.getInstance(application).taskDatabaseDao

        val taskViewFragmentArgs by navArgs<TaskDetailFragmentArgs>()

        viewModelFactory = TaskDetailViewModelFactory(dataSource, taskViewFragmentArgs.budgetId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TaskDetailViewModel::class.java)

        binding.viewModel = viewModel

        val chipGroup = binding.budgetList

        val chip = Chip(chipGroup.context)
        val chip2 = Chip(chipGroup.context)

        chip.text = getString(R.string.priority_tag_text)
        chip2.text = getString(R.string.add_tag_text)

        chip.chipBackgroundColor = context?.let {
            AppCompatResources.getColorStateList(
                it,
                R.color.selected_highlight
            )
        }
        chip2.chipBackgroundColor = context?.let {
            AppCompatResources.getColorStateList(
                it,
                R.color.selected_highlight
            )
        }

        chip.isClickable = true
        chip.isCheckable = true
        chip2.isClickable = true
        chip2.isCheckable = true

        chipGroup.addView(chip)
        chipGroup.addView(chip2)


        viewModel.budgetClicked.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                requireView().findNavController().navigate(
                    TaskDetailFragmentDirections
                    .actionBudgetDetailFragmentToBudgetEditFragment(it))
                viewModel.onBudgetClickedCompleted()
            }
        })

        priorityView.setOnClickListener {
            showBottomSheetDialog()
        }

        dueDateViewOne.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.MONTH, mMonth)
                selectedDate.set(Calendar.DAY_OF_MONTH, mDayOfMonth)
                val date = formater.format(selectedDate.time)
                viewModel.updateDueDate(date)
                dueDateText.setText(date)
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }


//        binding.imageEdit.setOnClickListener {
//            viewModel.onBudgetClicked(binding.textId.text.toString().toLong())
//        }
//
//        viewModel.budget.observe(viewLifecycleOwner, Observer {
//            (activity as AppCompatActivity).supportActionBar?.title = viewModel.budget.value?.tag
//        })


    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_priority, null)
        val dialog = context?.let { BottomSheetDialog(it) }

        dialog?.setContentView(view)

        view.viewUrgent.setOnClickListener {
            viewModel.updatePriority("Urgent")
            dialog?.dismiss()
        }
        view.viewHigh.setOnClickListener {
            viewModel.updatePriority("High")
            dialog?.dismiss()
        }
        view.viewMedium.setOnClickListener {
            viewModel.updatePriority("Medium")
            dialog?.dismiss()
        }
        view.viewLow.setOnClickListener {
            viewModel.updatePriority("Low")
            dialog?.dismiss()
        }
        dialog?.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {

            viewModel.deleteBudget()
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
