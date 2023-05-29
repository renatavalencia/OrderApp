package com.example.orderapp


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orderapp.adapter.ProductAdapter
import com.example.orderapp.databinding.FragmentOrderBinding
import com.example.orderapp.model.Cart
import com.example.orderapp.model.History
import com.example.orderapp.model.Product
import com.example.orderapp.network.ClientRetrofit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class OrderFragment : Fragment(), RecyclerViewClickListener {

  private var _binding: FragmentOrderBinding? = null
  private val binding get() = _binding!!
  var cartList: ArrayList<Cart> = ArrayList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentOrderBinding.inflate(inflater,container,false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    /*getCustomerList()
    binding.auTvCustomer.setOnClickListener {
      getCustomerList()
    }*/
    getProductList()
    loadCart()
    binding.btCart.setOnClickListener {
      requireActivity().startActivity(Intent(requireContext(), CartActivity::class.java))
    }

  }

  private fun getProductList() {
    val api = ClientRetrofit().getInstance()
    var productList: ArrayList<Product>? = null
    api.getProducts().enqueue(object : Callback<ArrayList<Product>> {
      override fun onResponse(
        call: Call<ArrayList<Product>>,
        response: Response<ArrayList<Product>>
      ) {
        if(response.isSuccessful) {
          productList = response.body()!!
          val adapter = ProductAdapter(productList!!)
          adapter.listener = this@OrderFragment
          binding.rvCartList.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(activity!!)
          }
        }
      }

      override fun onFailure(call: Call<ArrayList<Product>>, t: Throwable) {
        Toast.makeText(activity, "Fail to get products", Toast.LENGTH_SHORT).show()
      }

    })
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun loadCart() {
    val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
    val gson = Gson()
    val json = sharedPreferences.getString("cart", null)
    val type: Type = object : TypeToken<ArrayList<Cart?>?>() {}.type
    cartList = gson.fromJson(json, type)
  }

  private fun updateCart() {
    val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("user",
      AppCompatActivity.MODE_PRIVATE
    )
    val gson = Gson()
    val json = gson.toJson(cartList)
    sharedPreferences.edit().putString("cart",json).apply()
    updateTotalPrice()
  }

  private fun updateTotalPrice() {
    val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("user",
      AppCompatActivity.MODE_PRIVATE
    )
    var totalPrice: Int = 0
    for(cart in cartList) {
      totalPrice = totalPrice + Integer.parseInt(cart.product.product_price.toString()) * cart.qty
    }
    sharedPreferences.edit().putInt("totalprice",totalPrice).apply()
  }

  override fun onAddToCartClicked(view: View, product: Product, qty: Int) {
    for(cart in cartList) {
      if(cart.product.product_id.equals(product.product_id)){
        cartList.set(cartList.indexOf(cart),Cart(cart.qty + qty, product))
        updateCart()
        Toast.makeText(activity, "Added to cart", Toast.LENGTH_SHORT).show()
        return
      }
    }
    cartList.add(Cart(qty, product))
    updateCart()
    Toast.makeText(activity, "Added to cart", Toast.LENGTH_SHORT).show()
  }

  override fun onBtnDoneClicked(view: View, history: History) {
    TODO("Not yet implemented")
  }

  override fun onBtnCancelClicked(view: View, history: History) {
    TODO("Not yet implemented")
  }
}