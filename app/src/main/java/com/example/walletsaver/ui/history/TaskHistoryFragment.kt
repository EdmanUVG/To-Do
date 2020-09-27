package com.example.walletsaver.ui.history

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.walletsaver.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TaskHistoryFragment() : BottomSheetDialogFragment() {

    private var fragmentView: View? = null

    private lateinit var viewModel: TaskHistoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.task_history_fragment, container, false)

        return fragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    private fun initView() {

    }

}