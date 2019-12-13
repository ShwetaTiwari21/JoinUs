package com.example.joinus.joinusapp.Adapters

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.joinus.joinusapp.MyApplication
import com.example.joinus.joinusapp.R
import com.example.joinus.joinusapp.activities.MainActivity
import com.example.joinus.joinusapp.models.PollEvent
import com.example.joinus.joinusapp.models.ResponseModel
import com.example.joinus.joinusapp.models.SignupReqModel
import com.example.joinus.joinusapp.network.HeaderGenerator
import com.example.joinus.joinusapp.utils.AppUtils
import com.example.joinus.joinusapp.utils.Const
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.event_layout.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventAdapter(var context : Context,var openPollEventList:List<PollEvent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventViewHolder(LayoutInflater.from(context).inflate(R.layout.event_list_item , parent , false))
    }

    override fun getItemCount(): Int {
        return openPollEventList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            val viewHolder = holder as EventViewHolder
            viewHolder.tvTitile.setText(openPollEventList.get(position).poll_title)
            viewHolder.tvAuthor.setText(openPollEventList.get(position).created_by)
            viewHolder.tvJoinedNumber.setText(openPollEventList.get(position).interested_count)
            viewHolder.tvCategory.setText(openPollEventList.get(position).category)

            viewHolder.btJoin.setOnClickListener {

            }

            viewHolder.btLeave.setOnClickListener {

            }
        } catch (e:Exception){
            e.printStackTrace()
        }


    }

    inner class EventViewHolder(view : View): RecyclerView.ViewHolder(view){

        var cdImage : CardView = view.cd_image
        var tvTitile : TextView = view.tv_title
        var tvAuthor : TextView = view.tv_author
        var tvDescription : TextView = view.tv_description
        var tvJoinedNumber : TextView = view.tv_joined_number
        var tvExpireTime : TextView = view.tv_expire_time
        var tvCategory : TextView = view.tv_category
        var btJoin : TextView = view.bt_join
        var btLeave : TextView = view.bt_leave


    }

    private fun sendJoinReq(eventId:String){
        try {
            var joinReq = PollEvent(eventId)

            if (AppUtils.isNetworkConnected(context)) {
                var progressDialog  = ProgressDialog(context)
                progressDialog.setMessage("Loading....")
                progressDialog.show()

                val call = MyApplication.networkService.joinPollEvent(HeaderGenerator.createHeaderMap(context),joinReq)
                call.enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>?) {
                        progressDialog.dismiss()
                        if (response != null && response.body() != null && response.body().status.equals("OK")) {

                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        progressDialog.dismiss()
                        Log.e("errror", toString())
                        Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(context, "Network not connected!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun sendLeaveReq(eventId:String){
        try {
            var leaveReq = PollEvent(eventId)

            if (AppUtils.isNetworkConnected(context)) {
                var progressDialog  = ProgressDialog(context)
                progressDialog.setMessage("Loading....")
                progressDialog.show()

                val call = MyApplication.networkService.leavePollEvent(HeaderGenerator.createHeaderMap(context),leaveReq)
                call.enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>?) {
                        progressDialog.dismiss()
                        if (response != null && response.body() != null && response.body().status.equals("OK")) {

                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        progressDialog.dismiss()
                        Log.e("errror", toString())
                        Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(context, "Network not connected!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}