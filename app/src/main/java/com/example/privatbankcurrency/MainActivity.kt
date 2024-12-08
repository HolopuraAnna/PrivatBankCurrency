package com.example.privatbankcurrency

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.privatbankcurrency.databinding.ActivityMainBinding
import com.example.privatbankcurrency.item.ExchangeRate
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var daySpinner: Spinner
    private lateinit var monthSpinner: Spinner
    private lateinit var yearSpinner: Spinner
    private lateinit var currencyRecyclerView: RecyclerView
    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var binding: ActivityMainBinding
    private val currencyViewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Views
        daySpinner = findViewById(R.id.daySpinner)
        monthSpinner = findViewById(R.id.monthSpinner)
        yearSpinner = findViewById(R.id.yearSpinner)
        currencyRecyclerView = findViewById(R.id.currencyRecyclerView)
        currencyRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set up spinners
        setupSpinners()

        // Observe exchange rates
        val myObserver = Observer<List<ExchangeRate>> { exchangeRates ->
            currencyAdapter = CurrencyAdapter(exchangeRates)
            currencyRecyclerView.adapter = currencyAdapter
            Log.d("MainActivity", "Observed exchange rates: $exchangeRates")
        }
        currencyViewModel.exchangeRates.observe(this, myObserver)

        binding.currencyRecyclerView.layoutManager = LinearLayoutManager(this) ///

        // Observe error messages
        currencyViewModel.errorMessage.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        // Load initial data
        fetchExchangeRatesFromSpinners()
    }

    private fun setupSpinners() {
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH) + 1 // Month is 0-indexed
        val currentYear = calendar.get(Calendar.YEAR)

        val days = (1..31).map { it.toString() }
        val dayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpinner.adapter = dayAdapter

        val months = (1..12).map { it.toString().padStart(2, '0') }
        val monthAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        monthSpinner.adapter = monthAdapter

        val years = (2014..currentYear).map { it.toString() }
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = yearAdapter

        daySpinner.setSelection(days.indexOf(currentDay.toString()))
        monthSpinner.setSelection(months.indexOf(currentMonth.toString().padStart(2, '0')))
        yearSpinner.setSelection(years.indexOf(currentYear.toString()))

        daySpinner.onItemSelectedListener = dateSelectionListener
        monthSpinner.onItemSelectedListener = dateSelectionListener
        yearSpinner.onItemSelectedListener = dateSelectionListener
    }

    private val dateSelectionListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
            Log.d("MainActivity", "Spinner selected: ${parent?.selectedItem}")
            fetchExchangeRatesFromSpinners()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            Log.d("MainActivity", "No spinner selection")
        }
    }

    private fun fetchExchangeRatesFromSpinners() {
        val day = daySpinner.selectedItem.toString()
        val month = monthSpinner.selectedItem.toString()
        val year = yearSpinner.selectedItem.toString()

        val selectedDate = "$day.$month.$year" // Format: "dd.MM.yyyy"
        Log.d("MainActivity", "Fetching exchange rates for date: $selectedDate")
        currencyViewModel.fetchExchangeRates(selectedDate)
    }
}