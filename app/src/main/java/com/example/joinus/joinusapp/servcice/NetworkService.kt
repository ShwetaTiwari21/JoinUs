package com.example.joinus.joinusapp.servcice

import com.example.joinus.joinusapp.models.ResponseModel
import com.example.joinus.joinusapp.utils.Const
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST

/**
 * Created by shwetatiwari on 13/12/19.
 */

 interface NetworkService {

    @GET(Const.GET_API)
    fun getPollData (@HeaderMap headerMap: Map<String,String>, @Body body: Body): retrofit2.Call<ResponseModel>


}