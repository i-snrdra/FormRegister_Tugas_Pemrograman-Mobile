package com.isa.tugas1_register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var loginButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)

        val registeredUsername = intent.getStringExtra("username") ?: ""
        val registeredPassword = intent.getStringExtra("password") ?: ""
        val registeredFullName = intent.getStringExtra("fullName") ?: ""

        loginButton.setOnClickListener {
            validateAndLogin(registeredUsername, registeredPassword, registeredFullName)
        }
    }

    private fun validateAndLogin(registeredUsername: String, registeredPassword: String, registeredFullName: String) {
        val username = usernameInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (username.isEmpty()) {
            usernameInput.error = "Username tidak boleh kosong"
            return
        }

        if (password.isEmpty()) {
            showErrorDialog("Password tidak boleh kosong")
            return
        }

        if (username == registeredUsername && password == registeredPassword) {
            Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, WelcomeActivity::class.java).apply {
                putExtra("username", username)
                putExtra("fullName", registeredFullName)
            }
            startActivity(intent)
            finish()
        } else {
            showErrorDialog("Username atau password salah!")
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
} 