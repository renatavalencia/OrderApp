package com.example.orderapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.orderapp.RecyclerViewClickListener
import com.example.orderapp.databinding.ItemCartLayoutBinding
import com.example.orderapp.model.Product
import java.lang.Integer.parseInt
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(private val productList: ArrayList<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

  var listener: RecyclerViewClickListener? = null
  inner class ViewHolder(val binding: ItemCartLayoutBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
    val binding = ItemCartLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)

    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
    with(holder) {
      with(productList[position]) {
        binding.tvProductName.text = this.product_name
        binding.tvProductPrice.text= formatRupiah(this.product_price.toDouble())
        Glide.with(itemView.context)
          .load(this.product_url)
          .into(binding.ivProduct)

        with(binding.btAddCart) {
          this.isVisible = true
          this.setOnClickListener {
            listener?.onAddToCartClicked(it, productList[position], parseInt(binding.tvQty.text.toString()))
          }
        }

        binding.btMin.setOnClickListener {
          var qty = parseInt(binding.tvQty.text.toString())
          if(qty > 1) {
            qty--
            binding.tvQty.text = qty.toString()
          }
        }

        binding.btPlus.setOnClickListener {
          var qty = parseInt(binding.tvQty.text.toString())
          if(qty < 100) {
            qty++
            binding.tvQty.text = qty.toString()
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
    return productList.size
  }

}