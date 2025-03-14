package com.example.expensesplitterapp.Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expensesplitterapp.DataClasses.Balance
import com.example.expensesplitterapp.DataClasses.Expense
import com.example.expensesplitterapp.DataClasses.ExpenseSplit
import com.example.expensesplitterapp.DataClasses.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUser(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE email = :email ")
    suspend fun getUserByEmail(email: String): User?

    @Insert
    suspend fun insertExpense(expense: Expense): Long

    @Insert
    suspend fun insertExpenseSplit(expenseSplit: List<ExpenseSplit>)

    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): LiveData<List<Expense>>



}
