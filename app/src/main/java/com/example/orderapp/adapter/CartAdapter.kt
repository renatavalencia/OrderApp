package com.example.orderapp.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.orderapp.databinding.ItemCartLayoutBinding
import com.example.orderapp.model.Cart
import com.google.gson.Gson
import java.lang.Integer.parseInt
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(private val cartList: ArrayList<Cart>): RecyclerView.Adapter<CartAdapter.ViewHolder>() {

  inner class ViewHolder(val binding: ItemCartLayoutBinding) : RecyclerView.ViewHolder(binding.root)

  private var context: Context? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
    val binding = ItemCartLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    context = parent.context
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
    with(holder) {
      with(cartList[position]) {
        binding.tvProductName.text = this.product.product_name
        binding.tvProductPrice.text= formatRupiah(this.product.product_price.toDouble())
        Glide.with(itemView.context)
          .load(this.product.product_url)
          .into(binding.ivProduct)

        binding.btAddCart.isVisible = false
        binding.tvQty.text = this.qty.toString()

        binding.btMin.setOnClickListener {
          var qty = parseInt(binding.tvQty.text.toString())
          if(qty > 0) {
            qty--
            binding.tvQty.text = qty.toString()
            cartList.set(cartList.indexOf(this),Cart(qty, product))
            if(qty == 0) {
              cartList.removeAt(position)
              Toast.makeText(context, "Product removed", Toast.LENGTH_SHORT).show()
            }


            updateCart()
            notifyDataSetChanged()
          }
        }

        binding.btPlus.setOnClickListener {
          var qty = parseInt(binding.tvQty.text.toString())
          if(qty < 100) {
            qty++
            binding.tvQty.text = qty.toString()
            cartList.set(cartList.indexOf(this),Cart(qty, product))
            updateCart()
            notifyDataSetChanged()
          }
        }

      }
    }
  }

  private fun updateCart() {
    val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("user",
      AppCompatActivity.MODE_PRIVATE
    )
    val gson = Gson()
    val json = gson.toJson(cartList)
    sharedPreferences.edit().putString("cart",json).apply()
  }

  private fun formatRupiah(number: Double): String? {
    val localeID = Locale("in", "ID")
    val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
    return formatRupiah.format(number)
  }

  override fun getItemCount(): Int {
    return cartList.size
  }

}