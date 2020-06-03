package com.example.walletsaver.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walletsaver.R
import com.example.walletsaver.database.Budget
import com.example.walletsaver.databinding.BudgetItemLayoutBinding
import com.example.walletsaver.databinding.FragmentAddBudgetBinding
import kotlinx.android.synthetic.main.budget_item_layout.view.*

class BudgetAdapter internal constructor(
    context: Context
): RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var budgets = emptyList<Budget>()


    inner class BudgetViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val budgetName: TextView = itemView.findViewById(R.id.budget_name_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val itemView = inflater.inflate(R.layout.budget_item_layout, parent, false)
        return BudgetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val current = budgets[position]
        holder.budgetName.text = current.budgets
    }

    internal fun setBudgets(budgets: List<Budget>) {
        this.budgets = budgets
        notifyDataSetChanged()
    }

    override fun getItemCount() = budgets.size
}