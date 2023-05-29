package com.example.orderapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.orderapp.adapter.CustomerAdapter
import com.example.orderapp.databinding.ActivityPaymentDetailsBinding
import com.example.orderapp.model.Customer
import com.example.orderapp.model.LoginResponse
import com.example.orderapp.network.ClientRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class PaymentDetailsActivity : AppCompatActivity() {
  private lateinit var binding: ActivityPaymentDetailsBinding

  var customerId: String = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_payment_details)
    binding = ActivityPaymentDetailsBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setTodayDate()
    setSalesPerson()
    setTotalPrice()
    getCustomerList()
    binding.auTvCustomer.setOnClickListener {
      getCustomerList()
    }
    binding.btPay.setOnClickListener {
      if(customerId == "") {
        Toast.makeText(this@PaymentDetailsActivity, "Please select customer", Toast.LENGTH_SHORT).show()
      }
      else {
        postHistoryData()
      }
    }
  }

  private fun setTotalPrice() {
    val sharedPreferences: SharedPreferences = getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
    binding.tvTotalPrice.text = formatRupiah(sharedPreferences.getInt("totalprice",0).toDouble())
  }

  private fun formatRupiah(number: Double): String? {
    val localeID = Locale("in", "ID")
    val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
    return formatRupiah.format(number)
  }

  private fun postHistoryData() {
    val sharedPreferences: SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
    val userId = sharedPreferences.getString("userid","")
    val date = binding.tvDate.text.toString()
    val total = sharedPreferences.getInt("totalprice",0)
    val api = ClientRetrofit().getInstance()
    api.addHistory(userId!!, customerId!!, "On Going", date, total.toString()).enqueue(object : Callback<String> {
      override fun onResponse(call: Call<String>, response: Response<String>) {
        if(response.isSuccessful){
          if(response.body().equals("success")){
            startActivity(Intent(this@PaymentDetailsActivity, PaymentConfirmActivity::class.java))
            finish()
          }
          else {
            Toast.makeText(this@PaymentDetailsActivity, "Invalid payment", Toast.LENGTH_SHORT).show()
          }
        }
      }

      override fun onFailure(call: Call<String>, t: Throwable) {
        Toast.makeText(this@PaymentDetailsActivity, "Invalid payment", Toast.LENGTH_SHORT).show()
      }
    })
  }

  private fun setSalesPerson() {
    val sharedPreferences: SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
    binding.tvSalesPerson.text = sharedPreferences.getString("fullname","")
  }

  private fun setTodayDate() {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd MMMM yyyy")
    val current = formatter.format(time)
    binding.tvDate.text = current.toString()
  }

  private fun getCustomerList() {
    val api = ClientRetrofit().getInstance()
    var customerList: ArrayList<Customer>? = null
    api.getCustomers().enqueue(object : Callback<ArrayList<Customer>> {
      override fun onResponse(
        call: Call<ArrayList<Customer>>,
        response: Response<ArrayList<Customer>>
      ) {
        if(response.isSuccessful) {
          customerList = response.body()!!
          val arrayAdapter =
            CustomerAdapter(this@PaymentDetailsActivity, R.layout.item_customer_layout, customerList!!)
          binding.auTvCustomer.setAdapter(arrayAdapter)
          binding.auTvCustomer.setOnItemClickListener { parent, _, position, _ ->
            val customer = arrayAdapter.getItem(position) as Customer?
            binding.auTvCustomer.setText(customer?.customer_name)
            binding.tvPenerima.setText(customer?.customer_name)
            binding.tvPenerima.isVisible = true
            binding.tvAlamat.isVisible = true
            binding.tvAlamat.setText(customer?.customer_address)
            customerId = customer?.customer_id!!
          }
        }
      }

      override fun onFailure(call: Call<ArrayList<Customer>>, t: Throwable) {
        Toast.makeText(this@PaymentDetailsActivity, "Fail to get customer data", Toast.LENGTH_SHORT).show()
      }

    })
  }
}