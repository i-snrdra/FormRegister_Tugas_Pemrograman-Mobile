package com.isa.tugas3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.isa.tugas3.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var users: List<User> = emptyList()
    private var currentIndex = 0
    private val USD_TO_IDR = 16000.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateButtonStates()

        binding.btnGetUsers.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = RetrofitInstance.api.getUsers()
                    users = response.users
                    withContext(Dispatchers.Main) {
                        if (users.isNotEmpty()) {
                            currentIndex = 0
                            displayUser(0)
                            updateButtonStates()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                    }
                }
            }
        }

        binding.btnPrevious.setOnClickListener {
            if (users.isNotEmpty() && currentIndex > 0) {
                currentIndex--
                displayUser(currentIndex)
                updateButtonStates()
            }
        }

        binding.btnNext.setOnClickListener {
            if (users.isNotEmpty() && currentIndex < users.size - 1) {
                currentIndex++
                displayUser(currentIndex)
                updateButtonStates()
            }
        }

        binding.btnGetAllProduct.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = RetrofitInstance.api.getProducts()
                    val products = response.products
                    val avgUSD = if (products.isNotEmpty()) products.map { it.price }.average() else 0.0
                    val avgIDR = avgUSD * USD_TO_IDR
                    val formattedUSD = formatUSD(avgUSD)
                    val formattedIDR = formatRupiah(avgIDR)
                    withContext(Dispatchers.Main) {
                        binding.tvAveragePrice.text = "Harga rata-rata semua produk: $formattedUSD atau $formattedIDR"
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                    }
                }
            }
        }
    }

    private fun displayUser(index: Int) {
        val user = users[index]
        binding.tvFirstName.text = "First Name: ${user.firstName}"
        binding.tvLastName.text = "Last Name: ${user.lastName}"
        binding.tvUniversity.text = "University: ${user.university ?: "N/A"}"
    }

    private fun updateButtonStates() {
        binding.btnPrevious.isEnabled = users.isNotEmpty() && currentIndex > 0
        binding.btnNext.isEnabled = users.isNotEmpty() && currentIndex < users.size - 1
    }

    private fun formatUSD(amount: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        return formatter.format(amount)
    }

    private fun formatRupiah(amount: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return formatter.format(amount)
    }
}