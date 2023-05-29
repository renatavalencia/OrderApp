package com.example.orderapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.orderapp.RecyclerViewClickListener
import com.example.orderapp.databinding.ItemCartLayoutBinding
import com.example.orderapp.databinding.ItemHistoryLayoutBinding
import com.example.orderapp.model.Customer
import com.example.orderapp.model.History
import com.example.orderapp.model.Product
import java.lang.Integer.parseInt
import java.text.NumberFormat
import java.util.Locale

class HistoryAdapter(private val historyList: ArrayList<History>, private val customerList: ArrayList<Customer>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

  var listener: RecyclerViewClickListener? = null
  inner class ViewHolder(val binding: ItemHistoryLayoutBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
    val binding = ItemHistoryLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
    with(holder) {
      with(historyList[position]) {
        var customerName = ""
        for(customer in customerList) {
          if(customer.customer_id.equals(this.customer_id)) {
            customerName = customer.customer_name
          }
        }
        binding.tvCustomer.text = customerName
        binding.tvDate.text = this.order_date
        binding.tvStatus.text = this.order_status
        binding.tvTotalPrice.text= formatRupiah(this.total_price.toDouble())
        binding.tvOrderid.text = "Order ID " + this.order_id

        if(this.order_status.equals("On Going")) {
          binding.btDone.isEnabled = true
          binding.btCancel.isEnabled = true
        }
        else {
          binding.btDone.isEnabled = false
          binding.btCancel.isEnabled = false
        }

        with(binding.btDone) {
          this.setOnClickListener {
            listener?.onBtnDoneClicked(it, historyList[position])
          }
        }

        with(binding.btCancel) {
          this.setOnClickListener {
            listener?.onBtnCancelClicked(it, historyList[position])
          }
        }




      }
    }
  }

  private fun formatRupiah(number: Double): String? {
    val localeID = Locale("in", "ID")
    val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
    return formatRupiah.format(number)
  }

  override fun getItemCount(): Int {
    return historyList.size
  }

}