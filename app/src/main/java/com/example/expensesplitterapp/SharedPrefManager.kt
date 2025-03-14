package com.example.expensesplitterapp

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "expense_prefs"
        private const val KEY_TITLE = "title"
        private const val KEY_AMOUNT = "amount"
        private const val KEY_PARTICIPANTS = "participants"

        @Volatile
        private var instance: SharedPrefManager? = null

        fun getInstance(context: Context): SharedPrefManager {
            return instance ?: synchronized(this) {
                instance ?: SharedPrefManager(context).also { instance = it }
            }
        }
    }

    // Save expense
    fun saveExpense(title: String, amount: Double, participants: String) {
        sharedPreferences.edit().apply {
            putString(KEY_TITLE, title)
            putFloat(KEY_AMOUNT, amount.toFloat())
            putString(KEY_PARTICIPANTS, participants)
            apply()
        }
    }

    // Get expense title
    fun getTitle(): String? = sharedPreferences.getString(KEY_TITLE, null)

    // Get expense amount
    fun getAmount(): Double = sharedPreferences.getFloat(KEY_AMOUNT, 0.0F).toDouble()

    // Get participants
    fun getParticipants(): String? = sharedPreferences.getString(KEY_PARTICIPANTS, null)

    fun storeUserType(userType : String){
        sharedPreferences.edit().putString("userType","user").apply()
    }
    fun getUserType(): String? {
        return sharedPreferences.getString("userType", null) // Default value is null
    }


    // Clear all saved data
    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }
}