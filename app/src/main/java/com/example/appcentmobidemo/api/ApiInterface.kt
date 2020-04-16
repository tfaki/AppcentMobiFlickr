package com.example.appcentmobidemo.api

import com.example.appcentmobidemo.model.DataResponse
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {

    @POST("services/rest/")
    fun getPhotos(@Query("method") method: String,
                  @Query("api_key") api_key: String,
                  @Query("page") page: Int,
                  @Query("format") format: String,
                  @Query("nojsoncallback") nojsoncallback: Int) : Observable<DataResponse>
}