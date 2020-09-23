package com.example.walletsaver.ui.home

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.walletsaver.R
import com.example.walletsaver.database.WalletDatabase
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_add_task.*
import kotlinx.android.synthetic.main.dialog_priority_layout.*
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class BottomSheetAddTask() :  BottomSheetDialogFragment(){

    private var fragmentView: View? = null

    private lateinit var viewModel: BottomSheetAddTaskViewModel
    private lateinit var viewModelFactory: BottomSheetAddTaskViewModelFactory

    var formater = SimpleDateFormat("MMM dd", Locale.US)
    private var dueDate = ""
    private var priority = ""
    private var iconIndex = 5

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
                this.image_calendar.setImageResource(R.drawable.ic_baseline_calendar_selected)
//                view.dueDateText.setText(date.toString())
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        layout_priority.setOnClickListener {
            showPriorityDialog()
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
                // Get current date for date creation variable
                val now = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDateTime.now()
                } else { TODO("VERSION.SDK_INT < O") }

                val creationDate = now.format(DateTimeFormatter.ofPattern("MMM dd"))

                viewModel.insertTask(task, priority, "Mate",  dueDate, iconIndex, creationDate.toString())
                activity?.onBackPressed()
                activity?.let { UIUtil.hideKeyboard(it) }
            }
        }

    }

    private fun showPriorityDialog() {
        val dialog = context?.let {
            MaterialDialog(it)
                .noAutoDismiss()
                .customView(R.layout.dialog_priority_layout)
        }

        dialog?.findViewById<LinearLayout>(R.id.viewUrgent)?.setOnClickListener {
            priority = "Urgent"
            iconIndex = 1
            this.image_priority.setImageResource(R.drawable.ic_baseline_label_important_urgent)
            dialog.dismiss()
        }

        dialog?.findViewById<LinearLayout>(R.id.viewHigh)?.setOnClickListener {
            priority = "High"
            iconIndex = 2
            this.image_priority.setImageResource(R.drawable.ic_baseline_label_important_high)
            dialog.dismiss()
        }

        dialog?.findViewById<LinearLayout>(R.id.viewMedium)?.setOnClickListener {
            priority = "Medium"
            iconIndex = 3
            this.image_priority.setImageResource(R.drawable.ic_baseline_label_important_medium)
            dialog.dismiss()
        }

        dialog?.findViewById<LinearLayout>(R.id.viewLow)?.setOnClickListener {
            priority = "Low"
            iconIndex = 4
            this.image_priority.setImageResource(R.drawable.ic_baseline_label_important_low)
            dialog.dismiss()
        }

        dialog?.show()
    }
}