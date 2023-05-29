package com.example.orderapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orderapp.adapter.InventoryAdapter
import com.example.orderapp.databinding.FragmentInventoryBinding
import com.example.orderapp.model.Inventory
import com.example.orderapp.network.ClientRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InventoryFragment : Fragment() {

  private var _binding: FragmentInventoryBinding? = null
  private val binding get() = _binding!!
  var inventoryList: ArrayList<Inventory> = ArrayList()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentInventoryBinding.inflate(inflater,container,false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    getInventoryList()
  }

  private fun getInventoryList() {
    val api = ClientRetrofit().getInstance()
    api.getInventory().enqueue(object : Callback<ArrayList<Inventory>> {
      override fun onResponse(
        call: Call<ArrayList<Inventory>>,
        response: Response<ArrayList<Inventory>>
      ) {
        if(response.isSuccessful) {
          inventoryList = response.body()!!
          val adapter = InventoryAdapter(inventoryList)
          binding.rvInventoryList.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(activity!!)
          }
        }
      }

      override fun onFailure(call: Call<ArrayList<Inventory>>, t: Throwable) {
        Toast.makeText(activity, "Fail to get inventory", Toast.LENGTH_SHORT).show()
      }

    })
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}