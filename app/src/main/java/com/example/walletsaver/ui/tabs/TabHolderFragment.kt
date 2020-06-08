package com.example.walletsaver.ui.tabs

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.walletsaver.R
import com.example.walletsaver.ui.addbudget.AddBudgetFragment
import com.example.walletsaver.ui.addexpense.AddExpenseFragment
import com.example.walletsaver.ui.addincome.AddIncomeFragment
import kotlinx.android.synthetic.main.fragment_tab_holder.*

class TabHolderFragment : Fragment() {


    private lateinit var viewModel: TabHolderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_tab_holder, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(AddBudgetFragment(), "Presupuesto")
        adapter.addFragment(AddIncomeFragment(), "Ingreso")
        adapter.addFragment(AddExpenseFragment(), "Gasto")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

    }

}
