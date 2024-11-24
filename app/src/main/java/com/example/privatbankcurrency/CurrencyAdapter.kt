package com.example.privatbankcurrency

import android.content.Intent
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


    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val exchangeRate = exchangeRates[position]

        holder.currencyNameTextView.text = exchangeRate.currency ?: "Unknown Currency"

        // Check and display rates
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

//class CurrencyAdapter(
//    private var currencyList: List<ExchangeRate>,
//    private val onItemClick: (ExchangeRate) -> Unit
//) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val textView: TextView = itemView.findViewById(R.id.currencyNameTextView)
//
//        init {
//            // Define click listener for the ViewHolder's View
//            textView = view.findViewById(R.id.textView)
//        }
//    }
//
//    // !
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.currency_item, viewGroup, false)
//
//        return ViewHolder(view)
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//
//        // Get element from your dataset at this position and replace the
//        // contents of the view with that element
//        viewHolder.textView.text = dataSet[position]
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(currencyList[position], position == selectedPosition)
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    override fun getItemCount() = currencyList.size
//}