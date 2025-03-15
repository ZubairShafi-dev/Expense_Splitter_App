package com.example.expensesplitterapp.Ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensesplitterapp.DataClasses.Expense
import com.example.expensesplitterapp.ExpenseAdapter
import com.example.expensesplitterapp.Factory.UserViewModelFactory
import com.example.expensesplitterapp.R
import com.example.expensesplitterapp.SharedPrefManager
import com.example.expensesplitterapp.ViewModel.UserViewModel
import com.example.expensesplitterapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ExpenseAdapter.ClickHandler {
    private lateinit var binding : ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var list: List<Expense>
    private lateinit var adapter: ExpenseAdapter
    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefManager = SharedPrefManager.getInstance(this)
        userViewModel = ViewModelProvider(this, UserViewModelFactory(application))[UserViewModel::class.java]
        list = ArrayList()
        adapter = ExpenseAdapter(list,this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        userViewModel.allExpenses.observe(this) { expenses ->
//            Toast.makeText(this,"Size: ${expenses.size}",Toast.LENGTH_SHORT).show()
            adapter.updateList(expenses)
        }
        binding.addExpenseBtn.setOnClickListener {
            showAddExpenseDialog()
        }
        binding.logout.setOnClickListener {
            sharedPrefManager.clearData()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    private fun showAddExpenseDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_expense)

        val etTitle = dialog.findViewById<EditText>(R.id.etTitle)
        val etAmount = dialog.findViewById<EditText>(R.id.etAmount)
        val etParticipants = dialog.findViewById<EditText>(R.id.etParticipants)
        val btnAddExpense = dialog.findViewById<Button>(R.id.btnAddExpense)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btnAddExpense.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val amount = etAmount.text.toString().trim()
            val participants = etParticipants.text.toString()

            if (title.isNotEmpty() && amount.toDouble() > 0 && participants.isNotEmpty()) {
                val expenses = Expense(title =  title, amount =  amount.toDouble(), participants =  participants)
                userViewModel.addExpense(expenses)
                Toast.makeText(this, "Expense added successfully!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                etTitle.error = "Required"
                etAmount.error = "Required"
                etParticipants.error = "Required"
            }
        }

        dialog.show()
    }

    override fun onExpenseClick(expense: Expense) {
        val intent = Intent(this, BalanceActivity::class.java)
        intent.putExtra("expense_data", expense) // Passing expense object
        startActivity(intent)
    }


}