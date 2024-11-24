package com.example.privatbankcurrency

//import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.privatbankcurrency.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.nio.charset.Charset

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.privatbankcurrency.adapter.CurrencyAdapter
//import com.example.privatbankcurrency.viewmodel.CurrencyViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var daySpinner: Spinner
    private lateinit var monthSpinner: Spinner
    private lateinit var yearSpinner: Spinner
    private lateinit var currencyRecyclerView: RecyclerView
    private lateinit var currencyAdapter: CurrencyAdapter

    private val currencyViewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Views
        daySpinner = findViewById(R.id.daySpinner)
        monthSpinner = findViewById(R.id.monthSpinner)
        yearSpinner = findViewById(R.id.yearSpinner)
        currencyRecyclerView = findViewById(R.id.currencyRecyclerView)
        currencyRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set up spinners
        setupSpinners()

        // Observe exchange rates
        currencyViewModel.exchangeRates.observe(this, Observer { exchangeRates ->
            currencyAdapter = CurrencyAdapter(exchangeRates)
            currencyRecyclerView.adapter = currencyAdapter
        })

        // Observe error messages
        currencyViewModel.errorMessage.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        // Load initial data
        fetchExchangeRatesFromSpinners()
    }

    private fun setupSpinners() {
        // Get the current date
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH) + 1 // Month is 0-indexed
        val currentYear = calendar.get(Calendar.YEAR)

        // Populate day spinner
        val days = (1..31).map { it.toString() }
        val dayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpinner.adapter = dayAdapter

        // Populate month spinner
        val months = (1..12).map { it.toString().padStart(2, '0') }
        val monthAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        monthSpinner.adapter = monthAdapter

        // Populate year spinner
        val years = (2014..currentYear).map { it.toString() }
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = yearAdapter

        // Set default selections to the current date
        daySpinner.setSelection(days.indexOf(currentDay.toString()))
        monthSpinner.setSelection(months.indexOf(currentMonth.toString().padStart(2, '0')))
        yearSpinner.setSelection(years.indexOf(currentYear.toString()))

        // Add listeners to spinners
        daySpinner.onItemSelectedListener = dateSelectionListener
        monthSpinner.onItemSelectedListener = dateSelectionListener
        yearSpinner.onItemSelectedListener = dateSelectionListener
    }

    private val dateSelectionListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
            fetchExchangeRatesFromSpinners()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Do nothing
        }
    }

    private fun fetchExchangeRatesFromSpinners() {
        val day = daySpinner.selectedItem.toString()
        val month = monthSpinner.selectedItem.toString()
        val year = yearSpinner.selectedItem.toString()

        val selectedDate = "$day.$month.$year" // Format: "dd.MM.yyyy"
        currencyViewModel.fetchExchangeRates(selectedDate)
    }
}

//class MainActivity : AppCompatActivity() {
//    private val binding by lazy {
//        ActivityMainBinding.inflate(layoutInflater)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContentView(binding.root)
//
//        binding.button.setOnClickListener {
//            // TODO:
//            val url =
//                URL("https://itc.ua/articles/test-drajv-ds-7-e-tense-premyum-ne-za-vse-dengy-myra/")
//            lifecycleScope.launch {
//                val text = withContext(Dispatchers.IO) {
//                    url.readText(Charset.defaultCharset())
//                }
//                Log.d(TAG, "text: $text")
//            }
//
//        }
//    }
//
//
//    val TAG = "XXXXX"
//}