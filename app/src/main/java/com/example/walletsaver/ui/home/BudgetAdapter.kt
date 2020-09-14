package com.example.walletsaver.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walletsaver.database.Task
import com.example.walletsaver.databinding.TaskItemLayoutBinding

class BudgetAdapter (val listener: BudgetClickListener): ListAdapter<Task, BudgetAdapter.ViewHolder>(BudgetDiffCallback())  {

    override fun onBindViewHolder(holder: BudgetAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(listener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetAdapter.ViewHolder {
        return BudgetAdapter.ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: TaskItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: BudgetClickListener, item:Task) {
            binding.budget = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskItemLayoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class BudgetDiffCallback: DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.budgetId == newItem.budgetId
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}

class BudgetClickListener(val clickListener: (budgetid: Long) -> Unit) {
    fun onClick(task: Task) = clickListener(task.budgetId)
}