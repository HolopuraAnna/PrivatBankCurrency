package com.example.privatbankcurrency.retrofit

import com.example.privatbankcurrency.item.CurrencyItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//class RetrofitPrivate {
//    private val BASE_URL = "https://api.privatbank.ua/"
//
//    private val retrofit: Retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    private val service = retrofit.create(ServicePrivate::class.java)
//
//    fun getCurrencyExhange(data: String, resultCallback: (CurrencyItem?) -> Unit) {
//        val call = service.getExchangeRates(data)
//
//        call?.enqueue(object: Callback<CurrencyItem> {
//            override fun onResponse(
//                p0: Call<CurrencyItem>,
//                response: Response<CurrencyItem>
//            ) {
//                resultCallback(response.body())
//            }
//
//            override fun onFailure(p0: Call<CurrencyItem>?, throwable: Throwable) {
//                resultCallback(null)
//            }
//        })
//    }
//}

object RetrofitPrivate {
    private const val BASE_URL = "https://api.privatbank.ua/p24api/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: ServicePrivate by lazy {
        instance.create(ServicePrivate::class.java)
    }
}