package com.example.walletsaver.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walletsaver.database.Task
import com.example.walletsaver.databinding.TaskItemLayoutBinding

class TaskAdapter (val listener: TaskClickListener): ListAdapter<Task, TaskAdapter.ViewHolder>(TaskDiffCallback())  {

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(listener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder {
        return TaskAdapter.ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: TaskItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: TaskClickListener, item:Task) {
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


class TaskDiffCallback: DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}

class TaskClickListener(val clickListener: (budgetid: Long) -> Unit) {
    fun onClick(task: Task) = clickListener(task.taskId)
}