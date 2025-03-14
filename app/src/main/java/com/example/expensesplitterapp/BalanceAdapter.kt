package com.example.expensesplitterapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensesplitterapp.R

class BalanceAdapter(private val totalAmount: Double, private val totalPersons: Int) :
    RecyclerView.Adapter<BalanceAdapter.BalanceViewHolder>() {

    class BalanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personName: TextView = itemView.findViewById(R.id.textName)
        val amountOwed: TextView = itemView.findViewById(R.id.textAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_balance, parent, false)
        return BalanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        val perPersonShare = totalAmount / totalPersons  // Perform calculation here
        holder.personName.text = "Person ${position + 1}"
        holder.amountOwed.text = "Rs. %.2f".format(perPersonShare)
    }

    override fun getItemCount(): Int = totalPersons
}
