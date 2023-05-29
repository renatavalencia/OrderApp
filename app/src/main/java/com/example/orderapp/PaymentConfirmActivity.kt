package com.example.orderapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orderapp.databinding.ActivityPaymentConfirmBinding

class PaymentConfirmActivity : AppCompatActivity() {
  private lateinit var binding: ActivityPaymentConfirmBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_payment_confirm)
    binding = ActivityPaymentConfirmBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.btOk.setOnClickListener {
      startActivity(Intent(this@PaymentConfirmActivity, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
      finish()
    }
  }
}