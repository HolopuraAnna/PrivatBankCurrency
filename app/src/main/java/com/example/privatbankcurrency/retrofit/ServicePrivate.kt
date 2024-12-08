package com.example.privatbankcurrency.retrofit

import com.example.privatbankcurrency.item.CurrencyItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicePrivate {
    @GET("exchange_rates")
    fun getExchangeRates(@Query("date") courseData: String) : Call<CurrencyItem>?
}