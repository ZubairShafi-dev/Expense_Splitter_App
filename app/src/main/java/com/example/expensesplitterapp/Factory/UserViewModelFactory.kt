package com.example.expensesplitterapp.Factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensesplitterapp.Data.Repository
import com.example.expensesplitterapp.ViewModel.UserViewModel

class UserViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the requested ViewModel is UserViewModel
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            // Create and return UserViewModel with repository
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(application) as T
        }
        // If ViewModel class is not found, throw an exception
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
