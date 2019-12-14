package com.example.joinus.joinusapp.Adapters

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.CountDownTimer
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
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
import kotlinx.android.synthetic.main.event_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventAdapter(var activity: Activity,var pollEventList:List<PollEvent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventViewHolder(LayoutInflater.from(activity).inflate(R.layout.event_list_item , parent , false))
    }

    override fun getItemCount(): Int {
        return pollEventList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            val viewHolder = holder as EventViewHolder

            val pollEvent : PollEvent = pollEventList.get(position)
            viewHolder.tvTitile.text = pollEvent.poll_title
            viewHolder.tvAuthor.text = pollEvent.created_by
            viewHolder.tvJoinedNumber.text = "Joined By : ${pollEvent.interested_count}"
            viewHolder.tvCategory.text = "Category : ${pollEvent.category}"
            viewHolder.tvDescription.text = pollEvent.description

            if(pollEvent.is_interested){
                viewHolder.btJoin.visibility = View.GONE
                viewHolder.btLeave.visibility = View.VISIBLE
            } else {
                viewHolder.btJoin.visibility = View.VISIBLE
                viewHolder.btLeave.visibility = View.GONE
            }

            viewHolder.btJoin.setOnClickListener {
                sendJoinReq(pollEvent.poll_id.toString())
            }

            viewHolder.btLeave.setOnClickListener {
                sendLeaveReq(pollEvent.poll_id.toString())
            }

            val generator : ColorGenerator = ColorGenerator.MATERIAL
            val color1 : Int = generator.getRandomColor();
            val drawable : TextDrawable= TextDrawable.builder()
                .buildRound(pollEvent.created_by?.get(0)!!.toChar().toString(), color1)
            viewHolder.cdImage.background = drawable

            try {
                if(pollEvent.expiry_time != null) {
                    val timeRemaining: Long = pollEvent.expiry_time!!.toLong() - AppUtils.getCurrentTimestamp().toLong()
                    if(timeRemaining > 0) {
                        object : CountDownTimer(timeRemaining, 1000) {
                            override fun onTick(millis: Long) {
                                try {
                                    var seconds = (millis / 1000).toInt()

                                    val hours = seconds / (60 * 60)
                                    val tempMint = seconds - hours * 60 * 60
                                    val minutes = tempMint / 60
                                    seconds = tempMint - minutes * 60

                                    val time = (String.format("%02d", hours)
                                            + ":" + String.format("%02d", minutes)
                                            + ":" + String.format("%02d", seconds))
                                    //                        String time = String.format("%03d:%02d:%02d", l / 3600,
                                    //                                (l % 3600) / 60, (l % 60));
                                    Log.e("remainingtImee", time)
                                    val finalText = "Expiring In: $time"
                                    val spannableStringBuilder = SpannableStringBuilder(finalText)
                                    val foregroundColorSpan = ForegroundColorSpan(Color.RED)
                                    //32 is the starting index and 40 is the final index of counting time for making it red.
                                    spannableStringBuilder.setSpan(foregroundColorSpan, 13, 21, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                                    viewHolder.tvExpireTime.setText(spannableStringBuilder)

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }

                            override fun onFinish() {
                                viewHolder.tvExpireTime.setText("Expired")
                            }
                        }.start()
                    } else {
                        viewHolder.tvExpireTime.setText("Expired")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            viewHolder.ll_event.setOnClickListener {
                (activity as MainActivity).openDetailActivity(pollEvent.poll_id.toString())
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
        var ll_event : LinearLayout = view.ll_event

    }

    private fun sendJoinReq(eventId:String){
        try {
            var joinReq = PollEvent(eventId)

            if (AppUtils.isNetworkConnected(activity)) {
//                var progressDialog  = ProgressDialog(context)
//                progressDialog.setMessage("Loading....")
//                progressDialog.show()
                AppUtils.showLoader(activity)

                val call = MyApplication.networkService.joinPollEvent(HeaderGenerator.createHeaderMap(activity),joinReq)
                call.enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>?) {
                        AppUtils.removeLoader(activity)
//                        progressDialog.dismiss()
                        if (response != null && response.body() != null && response.body().status.equals("OK")) {
                            (activity as MainActivity).getAllPollData()
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        AppUtils.removeLoader(activity)
//                        progressDialog.dismiss()
                        Log.e("errror", toString())
                        Toast.makeText(activity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(activity, "Network not connected!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun sendLeaveReq(eventId:String){
        try {
            var leaveReq = PollEvent(eventId)

            if (AppUtils.isNetworkConnected(activity)) {
//                var progressDialog  = ProgressDialog(context)
//                progressDialog.setMessage("Loading....")
//                progressDialog.show()

                AppUtils.showLoader(activity)

                val call = MyApplication.networkService.leavePollEvent(HeaderGenerator.createHeaderMap(activity),leaveReq)
                call.enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>?) {
//                        progressDialog.dismiss()
                        AppUtils.removeLoader(activity)
                        if (response != null && response.body() != null && response.body().status.equals("OK")) {
                            (activity as MainActivity).getAllPollData()
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
//                        progressDialog.dismiss()
                        AppUtils.removeLoader(activity)
                        Log.e("errror", toString())
                        Toast.makeText(activity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(activity, "Network not connected!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}