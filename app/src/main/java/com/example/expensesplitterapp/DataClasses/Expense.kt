package com.example.expensesplitterapp.DataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val amount: Double,
//    val paidBy: String,  // Who paid the total amount
    val participants: String  // Comma-separated participant names
) : Serializable
