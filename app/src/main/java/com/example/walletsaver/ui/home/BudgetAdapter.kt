package com.example.walletsaver.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walletsaver.R
import com.example.walletsaver.database.Budget
import com.example.walletsaver.databinding.BudgetItemLayoutBinding

class BudgetAdapter (val listener: BudgetClickListener): ListAdapter<Budget, BudgetAdapter.ViewHolder>(BudgetDiffCallback())  {

    override fun onBindViewHolder(holder: BudgetAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(listener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetAdapter.ViewHolder {
        return BudgetAdapter.ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: BudgetItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: BudgetClickListener, item:Budget) {
            binding.budget = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BudgetItemLayoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class BudgetDiffCallback: DiffUtil.ItemCallback<Budget>() {
    override fun areItemsTheSame(oldItem: Budget, newItem: Budget): Boolean {
        return oldItem.budgetId == newItem.budgetId
    }

    override fun areContentsTheSame(oldItem: Budget, newItem: Budget): Boolean {
        return oldItem == newItem
    }
}

class BudgetClickListener(val clickListener: (budgetid: Long) -> Unit) {
    fun onClick(budget: Budget) = clickListener(budget.budgetId)
}