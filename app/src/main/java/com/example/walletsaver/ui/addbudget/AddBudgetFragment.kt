package com.example.walletsaver.ui.addbudget

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.walletsaver.R
import com.example.walletsaver.database.BudgetDatabase
import com.example.walletsaver.databinding.FragmentAddBudgetBinding
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_add_budget.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil

class AddBudgetFragment : Fragment() {

    private lateinit var viewModel: AddBudgetViewModel
    private lateinit var viewModelFactory: AddBudgetViewModelFactory

    private lateinit var binding: FragmentAddBudgetBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_budget, container, false)

        setHasOptionsMenu(true)



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val dataSource = BudgetDatabase.getInstance(application).budgetDatabaseDao

        viewModelFactory = AddBudgetViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddBudgetViewModel::class.java)

        val chipGroup = binding.budgetList
        val chip = Chip(chipGroup.context)
        val chip2 = Chip(chipGroup.context)

        chip.text = "Hogar"
        chip2.text = "Educacion"


        chip.isClickable = true
        chip.isCheckable = true
        chip2.isClickable = true
        chip2.isCheckable = true

        chipGroup.addView(chip)
        chipGroup.addView(chip2)


        binding.viewModel = viewModel

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            val budget = editText_budget.text.toString().trim()

            if (budget.isEmpty()) {
                editText_budget.error = "Presupuesto requerido"
                editText_budget.requestFocus()
            } else {
                viewModel.insertBudget()
                activity?.onBackPressed()
                Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()

                activity?.let { UIUtil.hideKeyboard(it) }
            }


        }
        return super.onOptionsItemSelected(item)
    }
}
