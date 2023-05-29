package com.example.orderapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.orderapp.RecyclerViewClickListener
import com.example.orderapp.databinding.ItemCartLayoutBinding
import com.example.orderapp.databinding.ItemInventoryLayoutBinding
import com.example.orderapp.model.Inventory
import com.example.orderapp.model.Product
import java.lang.Integer.parseInt
import java.text.NumberFormat
import java.util.Locale

class InventoryAdapter(private val inventoryList: ArrayList<Inventory>): RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {

  inner class ViewHolder(val binding: ItemInventoryLayoutBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryAdapter.ViewHolder {
    val binding = ItemInventoryLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: InventoryAdapter.ViewHolder, position: Int) {
    with(holder) {
      with(inventoryList[position]) {
        binding.tvProductName.text = this.product_name
        Glide.with(itemView.context)
          .load(this.product_url)
          .into(binding.ivProduct)
        binding.tvStock.text = this.product_stock
        binding.tvOrdered.text = this.product_ordered
        binding.tvAvailable.text= this.product_available
      }
    }
  }

  override fun getItemCount(): Int {
    return inventoryList.size
  }

}