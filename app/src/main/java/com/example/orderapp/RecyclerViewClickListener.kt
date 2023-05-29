package com.example.orderapp

import android.view.View
import com.example.orderapp.model.History
import com.example.orderapp.model.Product

interface RecyclerViewClickListener {
  fun onAddToCartClicked(view: View, product: Product, qty: Int)
  fun onBtnDoneClicked(view: View, history: History)
  fun onBtnCancelClicked(view: View, history: History)
}