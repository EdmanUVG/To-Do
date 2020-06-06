package com.example.walletsaver.ui.budgetdetail

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
import kotlinx.android.synthetic.main.fragment_add_budget.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil

class BudgetDetailFragment : Fragment() {

    companion object {
        fun newInstance() = BudgetDetailFragment()
    }

    private lateinit var viewModel: BudgetDetailViewModel
    private lateinit var binding: FragmentBudgetDetailBinding
    private lateinit var viewModelFactory: BudgetDetailViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_budget_detail, container, false)

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
