package com.example.walletsaver.ui.addincome

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.example.walletsaver.R
import com.example.walletsaver.database.BudgetDatabase
import com.example.walletsaver.databinding.FragmentAddIncomeBinding
import com.example.walletsaver.ui.addexpense.AddExpenseViewModel
import com.example.walletsaver.ui.addexpense.AddExpenseViewModelFactory
import kotlinx.android.synthetic.main.fragment_add_budget.*
import kotlinx.android.synthetic.main.fragment_add_income.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil


class AddIncomeFragment : Fragment() {

    private lateinit var binding: FragmentAddIncomeBinding
    private lateinit var viewModelFactory: AddIncomeViewModelFactory
    private lateinit var viewModel: AddIncomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_income, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application

        val dataSource = BudgetDatabase.getInstance(application).budgetDatabaseDao

        viewModelFactory = AddIncomeViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddIncomeViewModel::class.java)

        binding.viewModel = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            val income = editText_income.text.toString().trim()

            if (income.isEmpty()) {
                editText_budget.error = getString(R.string.income_required_text)
                editText_budget.requestFocus()
            } else {
                viewModel.addIncome("Income")
                activity?.onBackPressed()
                Toast.makeText(activity, getString(R.string.income_saved_text), Toast.LENGTH_SHORT).show()

                activity?.let { UIUtil.hideKeyboard(it) }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
