package com.example.orderapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.orderapp.databinding.ActivityLoginBinding
import com.example.orderapp.model.LoginResponse
import com.example.orderapp.network.ClientRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

  private lateinit var binding: ActivityLoginBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    binding = ActivityLoginBinding.inflate(layoutInflater)
    setContentView(binding.root)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      val w = window
      w.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
      )
    }
    val sharedPreferences: SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
    if(!sharedPreferences.getString("userid","").equals("")){
      startActivity(Intent(this@LoginActivity, MainActivity::class.java))
      finish()
    }


    binding.btLogin.setOnClickListener {
      val username = binding.etUsername.text.toString()
      val password = binding.etPassword.text.toString()

      if(username == "" || password == "") {
        Toast.makeText(this@LoginActivity, "Username & password can't be empty", Toast.LENGTH_SHORT).show()
      }
      else {
        getLoginResponse(username, password)
      }


    }
  }

  private fun getLoginResponse(username: String, password: String) {
    val api = ClientRetrofit().getInstance()
    api.login(username, password).enqueue(object : Callback<LoginResponse> {
      override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
        if(response.isSuccessful) {
          if(response?.body()?.response == true) {
            val sharedPreferences: SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
            sharedPreferences.edit().putString("userid", response.body()!!.user.user_id).apply()
            sharedPreferences.edit().putString("fullname", response.body()!!.user.full_name).apply()
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
          }
          else {
            Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
          }
        }
        else {
          Toast.makeText(this@LoginActivity, "Please try again", Toast.LENGTH_SHORT).show()
        }

      }

      override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        Log.e("error", t.message.toString())
      }

    })
  }
}