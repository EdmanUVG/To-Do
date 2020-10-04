package com.example.walletsaver.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walletsaver.database.Task
import com.example.walletsaver.databinding.TaskCompletedLayoutBinding

class TaskCompletedAdapter (val listeners: TaskClickListeners): ListAdapter<Task, TaskCompletedAdapter.ViewHolder>(TaskDiffCallbacks())  {

    override fun onBindViewHolder(holder: TaskCompletedAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(listeners, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskCompletedAdapter.ViewHolder {
        return TaskCompletedAdapter.ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: TaskCompletedLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListeners: TaskClickListeners, item:Task) {
            binding.task = item
            binding.clickListener = clickListeners
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskCompletedLayoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

}

// Change name if not works
class TaskDiffCallbacks: DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.taskId == newItem.taskId
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}

class TaskClickListeners(val clickListener: (task_id: Long) -> Unit) {
    fun onClick(task: Task) = clickListener(task.taskId)
}