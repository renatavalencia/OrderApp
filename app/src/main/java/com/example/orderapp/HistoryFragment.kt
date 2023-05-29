package com.example.orderapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orderapp.adapter.CustomerAdapter
import com.example.orderapp.adapter.HistoryAdapter
import com.example.orderapp.adapter.ProductAdapter
import com.example.orderapp.databinding.FragmentHistoryBinding
import com.example.orderapp.model.Customer
import com.example.orderapp.model.History
import com.example.orderapp.model.Product
import com.example.orderapp.network.ClientRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryFragment : Fragment(), RecyclerViewClickListener {

  private var _binding: FragmentHistoryBinding? = null
  private val binding get() = _binding!!
  var customerList: ArrayList<Customer> = ArrayList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentHistoryBinding.inflate(inflater,container,false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    getHistoryList()
  }

  private fun getHistoryList() {
    val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("user",
      AppCompatActivity.MODE_PRIVATE
    )
    var historyList: ArrayList<History>? = null
    val userId = sharedPreferences.getString("userid","")
    val api = ClientRetrofit().getInstance()
    api.getHistory(userId!!).enqueue(object : Callback<ArrayList<History>> {
      override fun onResponse(
        call: Call<ArrayList<History>>,
        response: Response<ArrayList<History>>
      ) {
        if(response.isSuccessful) {
          historyList = response.body()!!
          getCustomerList()
          val adapter = HistoryAdapter(historyList!!, customerList!!)
          adapter.listener = this@HistoryFragment
          binding.rvHistoryList.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(activity!!)
          }
        }
      }

      override fun onFailure(call: Call<ArrayList<History>>, t: Throwable) {
        Toast.makeText(activity, "Fail to get history", Toast.LENGTH_SHORT).show()
      }

    })
  }

  private fun getCustomerList() {
    val api = ClientRetrofit().getInstance()
    api.getCustomers().enqueue(object : Callback<ArrayList<Customer>> {
      override fun onResponse(
        call: Call<ArrayList<Customer>>,
        response: Response<ArrayList<Customer>>
      ) {
        if(response.isSuccessful) {
          customerList = response.body()!!
        }
      }
      override fun onFailure(call: Call<ArrayList<Customer>>, t: Throwable) {
      }

    })
  }

  private fun updateHistory(history: History, status: String) {
    val api = ClientRetrofit().getInstance()
    api.updateHistory(history.order_id, status).enqueue(object : Callback<String> {
      override fun onResponse(call: Call<String>, response: Response<String>) {
        if(response.isSuccessful){
          if(response.body().equals("success")){
            Toast.makeText(activity, "Order status changed", Toast.LENGTH_SHORT).show()
          }
        }
      }

      override fun onFailure(call: Call<String>, t: Throwable) {
      }
    })
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  override fun onAddToCartClicked(view: View, product: Product, qty: Int) {
    TODO("Not yet implemented")
  }

  override fun onBtnDoneClicked(view: View, history: History) {
    updateHistory(history,"Sent")
    getHistoryList()
  }

  override fun onBtnCancelClicked(view: View, history: History) {
    updateHistory(history,"Canceled")
    getHistoryList()
  }
}