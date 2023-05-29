package com.example.orderapp.network

import com.example.orderapp.model.Customer
import com.example.orderapp.model.History
import com.example.orderapp.model.Inventory
import com.example.orderapp.model.LoginResponse
import com.example.orderapp.model.Product
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {
  @FormUrlEncoded
  @POST("orderapp/login.php")
  fun login(
    @Field("username") username: String,
    @Field("password") password: String
  ): Call<LoginResponse>

  @GET("orderapp/customer.php")
  fun getCustomers() : Call<ArrayList<Customer>>

  @GET("orderapp/product.php")
  fun getProducts() : Call<ArrayList<Product>>

  @FormUrlEncoded
  @POST("orderapp/addHistory.php")
  fun addHistory(
    @Field("userid") userid: String,
    @Field("customerid") customerid: String,
    @Field("status") status: String,
    @Field("date") date: String,
    @Field("total") total: String
  ): Call<String>

  @FormUrlEncoded
  @POST("orderapp/updateHistory.php")
  fun updateHistory(
    @Field("orderid") orderid: String,
    @Field("status") status: String,
  ): Call<String>

  @FormUrlEncoded
  @POST("orderapp/history.php")
  fun getHistory(
    @Field("userid") userid: String
  ): Call<ArrayList<History>>

  @GET("orderapp/inventory.php")
  fun getInventory() : Call<ArrayList<Inventory>>

}