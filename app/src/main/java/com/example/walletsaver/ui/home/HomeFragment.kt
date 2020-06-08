package com.example.walletsaver.ui.home

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walletsaver.R
import com.example.walletsaver.database.BudgetDatabase
import com.example.walletsaver.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

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

        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_tab_holder_fragment)
        }
    }
}
