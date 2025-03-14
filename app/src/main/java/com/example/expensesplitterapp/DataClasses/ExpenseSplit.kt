package com.example.expensesplitterapp.DataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_split")
data class ExpenseSplit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val expenseId: Int,  // Foreign key reference to Expense table
    val participant: String,  // Name of the participant
    val amountOwed: Double  // Amount each participant owes
)
