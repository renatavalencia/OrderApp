package com.example.orderapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.orderapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    replaceFragment(OrderFragment())

    binding.bottomMenuNav.setOnItemSelectedListener { item ->
    when(item.itemId) {
      R.id.order -> replaceFragment(OrderFragment())
      R.id.history -> replaceFragment(HistoryFragment())
      R.id.inventory -> replaceFragment(InventoryFragment())
      R.id.logout -> {
        val sharedPreferences: SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        sharedPreferences.edit().putString("userid", "").apply()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
      }
    }
    true
    }
  }

  private fun replaceFragment(frag: Fragment) {
    supportFragmentManager.beginTransaction().replace(R.id.container, frag).commit()
  }
}