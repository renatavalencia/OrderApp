package com.example.orderapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientRetrofit {
  private fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
      .baseUrl("http://192.168.31.1/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  fun getInstance(): APIInterface {
    return getRetrofit().create(APIInterface::class.java)
  }
}