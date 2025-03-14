package com.example.expensesplitterapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.expensesplitterapp.DataClasses.Expense
import com.example.expensesplitterapp.databinding.ItemExpenseBinding

class ExpenseAdapter
    (private var list: List<Expense>, private var clickHandler: ClickHandler
            ):
    RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder>()
{
    inner class ExpenseHolder(val binding : ItemExpenseBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseHolder {
        return ExpenseHolder(ItemExpenseBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ExpenseHolder, position: Int) {
        val expense = list[position]
        holder.binding.tvExpenseTitle.text = "Title: ${expense.title}"
        holder.binding.tvExpenseAmount.text = "Amount: ${expense.amount}"
        holder.binding.tvParticipants.text = "Participant: ${expense.participants}"

        holder.itemView.setOnClickListener {
            clickHandler.onExpenseClick(expense)
        }

    }

    fun updateList(newList: List<Expense>){
        list = newList
        notifyDataSetChanged()
    }

    interface ClickHandler{
        fun onExpenseClick(expense: Expense)
    }
}