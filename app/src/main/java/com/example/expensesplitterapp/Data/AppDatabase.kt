package com.example.expensesplitterapp.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensesplitterapp.DataClasses.Expense
import com.example.expensesplitterapp.DataClasses.ExpenseSplit
import com.example.expensesplitterapp.DataClasses.User

@Database(entities = [User::class, Expense::class, ExpenseSplit::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "expense_splitter_database"
                )
                    .fallbackToDestructiveMigration()  // Prevents crashes due to schema changes
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
