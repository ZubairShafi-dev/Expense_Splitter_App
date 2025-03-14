package com.example.expensesplitterapp.Data

import androidx.lifecycle.LiveData
import com.example.expensesplitterapp.DataClasses.Balance
import com.example.expensesplitterapp.DataClasses.Expense
import com.example.expensesplitterapp.DataClasses.ExpenseSplit
import com.example.expensesplitterapp.DataClasses.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val userDao: UserDao) {
    val allExpenses: LiveData<List<Expense>> = userDao.getAllExpenses()


    suspend fun registerUser(user: User) {
        userDao.insertUser(user)
    }


    suspend fun loginUser(email: String, password: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUser(email, password)
        }
    }

    suspend fun addExpense(expense: Expense) {
        val expenseId = userDao.insertExpense(expense).toInt()
        val participantsList = expense.participants.split(",").map { it.trim() }
        val perPersonAmount = expense.amount / participantsList.size

        val splits = participantsList.map { participant ->
            ExpenseSplit(expenseId = expenseId, participant = participant, amountOwed = perPersonAmount)
        }

        userDao.insertExpenseSplit(splits)
    }

}
