package com.example.expensesplitterapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.expensesplitterapp.Data.AppDatabase
import com.example.expensesplitterapp.Data.Repository
import com.example.expensesplitterapp.DataClasses.Expense
import com.example.expensesplitterapp.DataClasses.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userDao = AppDatabase.getDatabase(application).userDao()
    private var repository = Repository(userDao)
    val allExpenses: LiveData<List<Expense>>
    init {
        repository = Repository(userDao)
        allExpenses = repository.allExpenses
    }

    fun registerUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.registerUser(user)
        }
    }
    fun loginUser(email: String, password: String, onResult: (User?) -> Unit) {
        viewModelScope.launch {
            val user = repository.loginUser(email, password)
            onResult(user)
        }
    }

    fun addExpense(expense: Expense) = viewModelScope.launch {
        repository.addExpense(expense)
    }



}
