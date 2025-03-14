package com.example.expensesplitterapp.Ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.expensesplitterapp.Data.AppDatabase
import com.example.expensesplitterapp.Data.Repository
import com.example.expensesplitterapp.DataClasses.User
import com.example.expensesplitterapp.Factory.UserViewModelFactory
import com.example.expensesplitterapp.R
import com.example.expensesplitterapp.ViewModel.UserViewModel
import com.example.expensesplitterapp.databinding.ActivitySignupBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var database: AppDatabase
    private lateinit var userViewModel: UserViewModel

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = AppDatabase.getDatabase(this)

        userViewModel = ViewModelProvider(this, UserViewModelFactory(application))[UserViewModel::class.java]

        binding.password.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2 // Right drawable index
                val drawable = binding.password.compoundDrawables[drawableEnd]

                if (drawable != null && event.rawX >= (binding.password.right - drawable.bounds.width())) {
                    if (binding.password.transformationMethod is PasswordTransformationMethod) {
                        // Show Password
                        binding.password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0)
                    } else {
                        // Hide Password
                        binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_remove_red_eye_24, 0)
                    }
                    binding.password.setSelection(binding.password.text.length) // Keep cursor at the end
                    return@setOnTouchListener true
                }
            }
            false
        }

        binding.signUpBtn.setOnClickListener {
            saveUserToRoom()
        }
    }

    private fun saveUserToRoom() {
        val name = binding.firstname.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.length < 6) {
            Toast.makeText(this, "Invalid Input!", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {
            val existingUser = database.userDao().getUserByEmail(email)
            if (existingUser == null) {
                userViewModel.registerUser(User(name = name, email = email, password = password))
                runOnUiThread {
                    Toast.makeText(this@SignupActivity, "User Registered!", Toast.LENGTH_SHORT).show()
                    finish() // Close activity after signup
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this@SignupActivity, "Email already exists!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}