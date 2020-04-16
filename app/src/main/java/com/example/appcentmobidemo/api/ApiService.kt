package com.example.appcentmobidemo.api

import com.example.appcentmobidemo.util.Const.BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiService {

    var gson = GsonBuilder()
        .setLenient()
        .create()

    val apiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build().create(ApiInterface::class.java)
}
