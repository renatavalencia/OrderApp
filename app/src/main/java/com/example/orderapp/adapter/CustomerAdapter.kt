package com.example.orderapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.example.orderapp.R
import com.example.orderapp.model.Customer

class CustomerAdapter(
  context: Context,
  @LayoutRes private val layoutResource: Int,
  private val customers: ArrayList<Customer>
) : ArrayAdapter<Customer>(context, layoutResource, customers) {

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view: View = LayoutInflater.from(context).inflate(layoutResource, parent, false)

    val customerName = view.findViewById<TextView>(R.id.tv_customer_name)

    customerName.setText(customers.get(position).customer_name)
    return view
  }

  override fun getItem(position: Int): Customer? {
    return customers.get(position)
  }

}