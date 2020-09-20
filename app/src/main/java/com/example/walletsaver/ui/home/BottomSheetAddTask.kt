package com.example.walletsaver.ui.home

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.walletsaver.R
import com.example.walletsaver.database.WalletDatabase
import com.example.walletsaver.databinding.BottomSheetAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_add_task.*
import kotlinx.android.synthetic.main.bottom_sheet_add_task.view.*
import kotlinx.android.synthetic.main.bottom_sheet_priority.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import java.text.SimpleDateFormat
import java.util.*


class BottomSheetAddTask() :  BottomSheetDialogFragment(){

    private var fragmentView: View? = null

    private lateinit var viewModel: BottomSheetAddTaskViewModel
    private lateinit var viewModelFactory: BottomSheetAddTaskViewModelFactory

    var formater = SimpleDateFormat("MMM dd", Locale.US)
    private var dueDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.bottom_sheet_add_task, container, false)
        return fragmentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val application = requireNotNull(this.activity).application

        val dataSourceBudget = WalletDatabase.getInstance(application).taskDatabaseDao

        viewModelFactory = BottomSheetAddTaskViewModelFactory(dataSourceBudget)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BottomSheetAddTaskViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        layout_calendar.setOnClickListener {
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.MONTH, mMonth)
                selectedDate.set(Calendar.DAY_OF_MONTH, mDayOfMonth)
                val date = formater.format(selectedDate.time)
                dueDate = date.toString()
//                view.dueDateText.setText(date.toString())
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        layout_priority.setOnClickListener {
            Toast.makeText(context, "Priority", Toast.LENGTH_SHORT).show()
        }

        layout_tag.setOnClickListener {
            Toast.makeText(context, "Tag", Toast.LENGTH_SHORT).show()
        }

        layout_save.setOnClickListener {
            val task = editText_task.text.toString().trim()

            if (task.isEmpty()) {
                editText_task.error = getString(R.string.budget_required_text)
                editText_task.requestFocus()
            } else {
                viewModel.insertTask(task, "Medium", "Mate",  dueDate, 2, "Today")
                activity?.onBackPressed()

                activity?.let { UIUtil.hideKeyboard(it) }
            }
        }

    }
}