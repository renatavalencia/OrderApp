package com.example.orderapp.model

data class History (
  val order_id: String,
  val user_id: String,
  val customer_id: String,
  val order_status: String,
  val order_date: String,
  val total_price: String
)