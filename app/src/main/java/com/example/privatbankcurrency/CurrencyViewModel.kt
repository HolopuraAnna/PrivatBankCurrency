package com.example.privatbankcurrency

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.privatbankcurrency.item.CurrencyItem
import com.example.privatbankcurrency.item.ExchangeRate
import com.example.privatbankcurrency.retrofit.RetrofitPrivate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrencyViewModel : ViewModel() {

    private val _exchangeRates = MutableLiveData<List<ExchangeRate>>()
    val exchangeRates: LiveData<List<ExchangeRate>> get() = _exchangeRates

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchExchangeRates(date: String) {
        val service = RetrofitPrivate.service
        val call = service.getExchangeRates(date)
        Log.d("CurrencyViewModel", "Fetching rates for date: $date")

        call?.enqueue(object : Callback<CurrencyItem> {
            override fun onResponse(call: Call<CurrencyItem>, response: Response<CurrencyItem>) {
                Log.d("CurrencyViewModel", "response: $response")
                if (response.isSuccessful) {
                    val rates = response.body()?.exchangeRate?.filterNotNull() ?: emptyList()
                    _exchangeRates.postValue(rates)
                } else {
                    Log.e("CurrencyViewModel", "Response failed: Code ${response.code()}, Message: ${response.message()}")
                    _errorMessage.postValue("Failed to load data: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CurrencyItem>, t: Throwable) {
                _errorMessage.postValue("Error: ${t.message}")
            }
        })
    }
}