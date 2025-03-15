package com.example.expensesplitterapp.Ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.expensesplitterapp.Factory.UserViewModelFactory
import com.example.expensesplitterapp.R
import com.example.expensesplitterapp.SharedPrefManager
import com.example.expensesplitterapp.ViewModel.UserViewModel
import com.example.expensesplitterapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPrefManager: SharedPrefManager

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this, UserViewModelFactory(application))[UserViewModel::class.java]
        sharedPrefManager = SharedPrefManager.getInstance(this)

        binding.signUp.setOnClickListener{
            startActivity(Intent(this,SignupActivity::class.java))
        }
        binding.password.setOnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action === MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.password.right - binding.password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())
                ) {
                    if (binding.password.transformationMethod
                            .equals(PasswordTransformationMethod.getInstance())
                    ) {
                        binding.password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.baseline_visibility_off_24,
                            0
                        )
                    } else {
                        binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.baseline_remove_red_eye_24,
                            0
                        )
                    }
                    binding.password.setSelection(binding.password.getText().length)
                    return@setOnTouchListener true
                }
            }
            false
        }


        // Handle Login Button Click
        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                userViewModel.loginUser(email, password) { user ->
                    if (user != null) {
                        sharedPrefManager.storeUserType("user")
                        Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}