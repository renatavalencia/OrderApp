package com.example.orderapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orderapp.adapter.CartAdapter
import com.example.orderapp.databinding.ActivityCartBinding
import com.example.orderapp.model.Cart
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CartActivity : AppCompatActivity() {
  private lateinit var binding: ActivityCartBinding
  var cartList: ArrayList<Cart> = ArrayList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_cart)
    binding = ActivityCartBinding.inflate(layoutInflater)
    setContentView(binding.root)
    loadCart()
    initRvCartList()
    binding.btOrder.setOnClickListener {
      if(!cartList.size.equals(0)) {
        startActivity(Intent(this@CartActivity, PaymentDetailsActivity::class.java))
        updateTotalPrice()
      }
      else {
        Toast.makeText(this@CartActivity, "Cart is empty", Toast.LENGTH_SHORT).show()
      }
    }

  }

  private fun updateTotalPrice() {
    loadCart()
    val sharedPreferences: SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
    var totalPrice: Int = 0
    for(cart in cartList) {
      totalPrice = totalPrice + Integer.parseInt(cart.product.product_price.toString()) * cart.qty
    }
    sharedPreferences.edit().putInt("totalprice",totalPrice).apply()
  }

  private fun initRvCartList() {
    if(cartList != null) {
      val adapter = CartAdapter(cartList)
      binding.rvCartList.apply {
        this.adapter = adapter
        this.layoutManager = LinearLayoutManager(this@CartActivity)
      }
    }
  }

  private fun loadCart() {
    val sharedPreferences: SharedPreferences = getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
    val gson = Gson()
    val json = sharedPreferences.getString("cart", null)
    val type: Type = object : TypeToken<ArrayList<Cart?>?>() {}.type
    cartList = gson.fromJson(json, type)
  }
}