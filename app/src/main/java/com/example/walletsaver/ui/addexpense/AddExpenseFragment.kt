package com.example.walletsaver.ui.addexpense

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.walletsaver.R

class AddExpenseFragment : Fragment() {

    companion object {
        fun newInstance() = AddExpenseFragment()
    }

    private lateinit var viewModel: AddExpenseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_expense, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddExpenseViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
