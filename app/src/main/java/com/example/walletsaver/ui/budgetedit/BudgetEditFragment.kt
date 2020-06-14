package com.example.walletsaver.ui.budgetedit

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs

import com.example.walletsaver.R
import com.example.walletsaver.database.BudgetDatabase
import com.example.walletsaver.databinding.FragmentBudgetEditBinding
import com.example.walletsaver.ui.budgetdetail.BudgetDetailFragmentArgs
import com.example.walletsaver.ui.budgetdetail.BudgetDetailViewModel
import com.example.walletsaver.ui.budgetdetail.BudgetDetailViewModelFactory
import kotlinx.android.synthetic.main.fragment_budget_edit.*

class BudgetEditFragment : Fragment() {

    private lateinit var binding: FragmentBudgetEditBinding
    private lateinit var viewModel: BudgetEditViewModel
    private lateinit var viewModelFactory: BudgetEditViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_budget_edit, container, false)

        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        val application = requireNotNull(this.activity).application
        val dataSource = BudgetDatabase.getInstance(application).budgetDatabaseDao

        val budgetViewFragmentArgs by navArgs<BudgetEditFragmentArgs>()

        viewModelFactory = BudgetEditViewModelFactory(dataSource, budgetViewFragmentArgs.budgetId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BudgetEditViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.budget.observe(viewLifecycleOwner, Observer {
            (activity as AppCompatActivity).supportActionBar?.title = viewModel.budget.value?.category
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {

            val budget = editText_edit.text.toString().trim()

            viewModel.updateBudget(Integer.parseInt(budget))
            activity?.onBackPressed()
            Toast.makeText(activity, "Updated", Toast.LENGTH_SHORT).show()

        }
        return super.onOptionsItemSelected(item)
    }

}
