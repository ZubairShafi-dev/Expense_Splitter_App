package com.example.expensesplitterapp.Ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensesplitterapp.Adapters.BalanceAdapter
import com.example.expensesplitterapp.Data.AppDatabase
import com.example.expensesplitterapp.Data.Repository
import com.example.expensesplitterapp.DataClasses.Expense
import com.example.expensesplitterapp.Factory.UserViewModelFactory
import com.example.expensesplitterapp.R
import com.example.expensesplitterapp.ViewModel.UserViewModel

class BalanceActivity : AppCompatActivity() {
    private lateinit var balanceAdapter: BalanceAdapter
    private lateinit var persons: TextView
    private lateinit var amount: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBalance)
        recyclerView.layoutManager = LinearLayoutManager(this)
        amount = findViewById(R.id.amount)
        persons = findViewById(R.id.persons)

        val expense = intent.getSerializableExtra("expense_data") as? Expense

        if (expense != null) {
            amount.text = "Total Amount: ${expense.amount}"
            persons.text = "Total Persons: ${expense.participants}"

            balanceAdapter = BalanceAdapter(expense.amount, expense.participants.toInt())
            recyclerView.adapter = balanceAdapter
        } else {
            Toast.makeText(this, "Expense data not found", Toast.LENGTH_SHORT).show()
        }
    }
}
