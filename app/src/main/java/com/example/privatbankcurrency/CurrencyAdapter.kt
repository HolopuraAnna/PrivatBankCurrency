package com.example.privatbankcurrency

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.privatbankcurrency.item.ExchangeRate

class CurrencyAdapter(private val exchangeRates: List<ExchangeRate>) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_item, parent, false)
        return CurrencyViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val exchangeRate = exchangeRates[position]

        holder.currencyNameTextView.text = exchangeRate.currency ?: "Unknown Currency"

        if (exchangeRate.saleRate != null && exchangeRate.purchaseRate != null) {
            holder.saleRateTextView.text = "Sale Rate: ${exchangeRate.saleRate}"
            holder.purchaseRateTextView.text = "Purchase Rate: ${exchangeRate.purchaseRate}"
        } else {
            holder.saleRateTextView.text = "Sale Rate (NB): ${exchangeRate.saleRateNB ?: "N/A"}"
            holder.purchaseRateTextView.text = "Purchase Rate (NB): ${exchangeRate.purchaseRateNB ?: "N/A"}"
        }
    }


    override fun getItemCount(): Int = exchangeRates.size

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currencyNameTextView: TextView = itemView.findViewById(R.id.currencyNameTextView)
        val saleRateTextView: TextView = itemView.findViewById(R.id.saleRateTextView)
        val purchaseRateTextView: TextView = itemView.findViewById(R.id.purchaseRateTextView)
    }
}