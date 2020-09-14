package com.example.walletsaver.ui.addtask

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.walletsaver.R
import com.example.walletsaver.database.WalletDatabase
import com.example.walletsaver.databinding.FragmentAddTaskBinding
import com.google.android.material.chip.Chip
import com.santalu.maskedittext.MaskEditText
import kotlinx.android.synthetic.main.fragment_add_task.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil

class AddTaskFragment : Fragment() {

    private lateinit var viewModel: AddTaskViewModel
    private lateinit var viewModelFactory: AddTaskViewModelFactory

    private lateinit var binding: FragmentAddTaskBinding

    private lateinit var maskEditText: MaskEditText


    private var category = ""
    private var iconIndex = 7


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_task, container, false)

        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val dataSource = WalletDatabase.getInstance(application).taskDatabaseDao

        viewModelFactory = AddTaskViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddTaskViewModel::class.java)

        val chipGroup = binding.budgetList

        val chip = Chip(chipGroup.context)
        val chip2 = Chip(chipGroup.context)
        val chip3 = Chip(chipGroup.context)
        val chip4 = Chip(chipGroup.context)
        val chip5 = Chip(chipGroup.context)
        val chip6 = Chip(chipGroup.context)

        chip.text = getString(R.string.home_category_text)
        chip2.text = getString(R.string.education_category_text)
        chip3.text = getString(R.string.transportation_category_text)
        chip4.text = getString(R.string.finance_category_text)
        chip5.text = getString(R.string.food_category_text)
        chip6.text = getString(R.string.entertainment_category_text)

        chip.chipBackgroundColor = context?.let { getColorStateList(it, R.color.selected_highlight) }
        chip2.chipBackgroundColor = context?.let { getColorStateList(it, R.color.selected_highlight) }
        chip3.chipBackgroundColor = context?.let { getColorStateList(it, R.color.selected_highlight) }
        chip4.chipBackgroundColor = context?.let { getColorStateList(it, R.color.selected_highlight) }
        chip5.chipBackgroundColor = context?.let { getColorStateList(it, R.color.selected_highlight) }
        chip6.chipBackgroundColor = context?.let { getColorStateList(it, R.color.selected_highlight) }

        chip.isClickable = true
        chip.isCheckable = true
        chip2.isClickable = true
        chip2.isCheckable = true
        chip3.isClickable = true
        chip3.isCheckable = true
        chip4.isClickable = true
        chip4.isCheckable = true
        chip5.isClickable = true
        chip5.isCheckable = true
        chip6.isClickable = true
        chip6.isCheckable = true

        chip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                category = chip.text.toString()
                iconIndex = 1
            }
        }
        chip2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                category = chip2.text.toString()
                iconIndex = 2
            }
        }
        chip3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                category = chip3.text.toString()
                iconIndex = 3
            }
        }
        chip4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                category = chip4.text.toString()
                iconIndex = 4
            }
        }
        chip5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                category = chip5.text.toString()
                iconIndex = 5
            }
        }
        chip6.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                category = chip6.text.toString()
                iconIndex = 6
            }
        }

        chipGroup.addView(chip)
        chipGroup.addView(chip2)
        chipGroup.addView(chip3)
        chipGroup.addView(chip4)
        chipGroup.addView(chip5)
        chipGroup.addView(chip6)

        binding.viewModel = viewModel

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {

            val budget = editText_budget.text.toString().trim().replace(",", "")

            
            if (budget.isEmpty()) {
                editText_budget.error = getString(R.string.budget_required_text)
                editText_budget.requestFocus()
            } else {
                viewModel.insertBudget(budget, category, iconIndex)
                activity?.onBackPressed()
                Toast.makeText(activity, getString(R.string.budget_saved_text), Toast.LENGTH_SHORT).show()

                activity?.let { UIUtil.hideKeyboard(it) }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
