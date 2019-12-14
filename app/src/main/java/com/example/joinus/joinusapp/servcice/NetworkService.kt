package com.example.joinus.joinusapp.servcice

import com.example.joinus.joinusapp.models.GetAllPollResponse
import com.example.joinus.joinusapp.models.PollEvent
import com.example.joinus.joinusapp.models.ResponseModel
import com.example.joinus.joinusapp.models.SignupReqModel
import com.example.joinus.joinusapp.utils.Const
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by shwetatiwari on 13/12/19.
 */

 interface NetworkService {

   @GET(Const.GET_API)
   fun getPollData(@Query("username") username: String): retrofit2.Call<GetAllPollResponse>

   @POST(Const.SIGNUP_API)
   fun signUp(@Body body: SignupReqModel): retrofit2.Call<ResponseModel>

   @POST(Const.LOGIN_API)
   fun login(@Body body: SignupReqModel): retrofit2.Call<ResponseModel>


   @POST(Const.POLL_CREATE_API)
   fun createPollEvent(@HeaderMap headers: Map<String,String>, @Body body: PollEvent): retrofit2.Call<ResponseModel>

  @POST(Const.EVENT_DETAIL_API)
  fun getEventDetail(@HeaderMap headers: Map<String,String>, @Body body: PollEvent): retrofit2.Call<ResponseModel>

   @POST(Const.JOIN_API)
   fun joinPollEvent(@HeaderMap headers: Map<String,String>?, @Body body: PollEvent): retrofit2.Call<ResponseModel>

   @POST(Const.LEAVE_API)
   fun leavePollEvent(@HeaderMap headers: Map<String,String>?, @Body body: PollEvent): retrofit2.Call<ResponseModel>

   @POST(Const.UPDATE_API)
   fun updatePollEvent(@HeaderMap headers: Map<String,String>?, @Body body: PollEvent): retrofit2.Call<ResponseModel>

   @POST(Const.DELETE_API)
   fun deletePollEvent(@HeaderMap headers: Map<String,String>?, @Body body: PollEvent): retrofit2.Call<ResponseModel>


}