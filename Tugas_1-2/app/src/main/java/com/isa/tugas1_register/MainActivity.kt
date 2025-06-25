package com.isa.tugas1_register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var fullNameInput: TextInputEditText
    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var registerButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fullNameInput = findViewById(R.id.fullNameInput)
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            validateAndRegister()
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

    private fun showSuccessDialog(fullName: String, username: String, password: String) {
        AlertDialog.Builder(this)
            .setTitle("Success")
            .setMessage("Registrasi berhasil!")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                val intent = Intent(this, LoginActivity::class.java).apply {
                    putExtra("username", username)
                    putExtra("password", password)
                    putExtra("fullName", fullName)
                }
                startActivity(intent)
                finish()
            }
            .show()
    }

    private fun validateAndRegister() {
        val fullName = fullNameInput.text.toString().trim()
        val username = usernameInput.text.toString().trim()
        val password = passwordInput.text.toString()
        val confirmPassword = confirmPasswordInput.text.toString()

        if (fullName.isEmpty()) {
            fullNameInput.error = "Nama Lengkap tidak boleh kosong"
            return
        }

        if (username.isEmpty()) {
            usernameInput.error = "Username tidak boleh kosong"
            return
        }

        if (password.isEmpty()) {
            showErrorDialog("Password tidak boleh kosong")
            return
        }

        if (confirmPassword.isEmpty()) {
            showErrorDialog("Konfirmasi password tidak boleh kosong")
            return
        }

        if (password != confirmPassword) {
            showErrorDialog("Ulangi kedua password dengan benar")
            passwordInput.text?.clear()
            confirmPasswordInput.text?.clear()
            return
        }

        showSuccessDialog(fullName, username, password)
    }
}