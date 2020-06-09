package com.example.walletsaver.ui.tabs

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

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
        adapter.addFragment(AddIncomeFragment(), "Ingreso")
        adapter.addFragment(AddBudgetFragment(), "Presupuesto")
        adapter.addFragment(AddExpenseFragment(), "Gasto")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        tabs!!.post (Runnable {
           tabs!!.setupWithViewPager(viewPager)})
        selectDefaultTab(1)
    }

    private fun selectDefaultTab(index: Int) {
        Handler().postDelayed(Runnable {
            tabs!!.setScrollPosition(index, 0f, true)
            viewPager!!.currentItem = index
        }, 100)
    }
}
