package com.example.joinus.joinusapp.activities

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.widget.LinearLayoutManager
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.joinus.joinusapp.Adapters.CandidateDetailAdapter
import com.example.joinus.joinusapp.Adapters.EventAdapter
import com.example.joinus.joinusapp.MyApplication
import com.example.joinus.joinusapp.R
import com.example.joinus.joinusapp.models.*
import com.example.joinus.joinusapp.network.HeaderGenerator
import com.example.joinus.joinusapp.utils.AppUtils
import com.example.joinus.joinusapp.utils.Const
import kotlinx.android.synthetic.main.activity_category_detail.*
import kotlinx.android.synthetic.main.activity_event_detail.*
import kotlinx.android.synthetic.main.event_layout.*
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventDetailActivity : AppCompatActivity() {

    lateinit var event_id : String
    lateinit var linearLayoutManager : LinearLayoutManager
    lateinit var candidateDetailAdapter: CandidateDetailAdapter
    lateinit var interestedCandidates : List<CandidateModel>
    lateinit var eventDetails : PollEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        if(intent != null) {
            event_id = intent.getStringExtra(Const.EVENT_ID)
            interestedCandidates = ArrayList()
            getEventDetail(event_id)
        }
    }

    private fun getEventDetail(eventId: String) {
            try {
                if (AppUtils.isNetworkConnected(this@EventDetailActivity)) {
                    AppUtils.showLoader(this@EventDetailActivity)
                    val username = AppUtils.getSharedPrefs(this).getString(Const.SHARED_PREF_USERNAME,"")
                    val call = MyApplication.networkService.getEventDetail(HeaderGenerator.createHeaderMap(this@EventDetailActivity) , eventId)

                    call.enqueue(object : Callback<GetParticularPollResponse> {
                        override fun onResponse(call: Call<GetParticularPollResponse>, response: Response<GetParticularPollResponse>?) {
//                        progressDialog.dismiss()
                            AppUtils.removeLoader(this@EventDetailActivity)
                            if (response != null && response.body() != null && response.body().status.equals("OK")) {
                                interestedCandidates = response.body().interested_users
                                eventDetails = response.body().details
                                setupRecyclerView()
                                setupView()
                            }
                        }

                        override fun onFailure(call: Call<GetParticularPollResponse>, t: Throwable) {
//                        progressDialog.dismiss()
                            AppUtils.removeLoader(this@EventDetailActivity)
                            Log.e("errror", toString())
                            Toast.makeText(this@EventDetailActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(this@EventDetailActivity, "Network not connected!", Toast.LENGTH_SHORT).show()
                }
        }catch (e : Exception){
            e.printStackTrace()
        }

    }

    private fun setupView() {
        tv_title.text = eventDetails.poll_title
        tv_author.text = eventDetails.created_by
        tv_description.text = eventDetails.description

        tv_joined_number.text = "Joined By : ${eventDetails.interested_count}"
        tv_category.text = "Category : ${eventDetails.category}"

        if(eventDetails.is_interested){
            bt_join.visibility = View.GONE
            bt_leave.visibility = View.VISIBLE
        } else {
            bt_join.visibility = View.VISIBLE
            bt_leave.visibility = View.GONE
        }

        bt_join.setOnClickListener {
            sendJoinReq(eventDetails.poll_id.toString())
        }

        bt_leave.setOnClickListener {
            sendLeaveReq(eventDetails.poll_id.toString())
        }


        val generator : ColorGenerator = ColorGenerator.MATERIAL
        val color1 : Int = generator.getRandomColor();
        val drawable : TextDrawable = TextDrawable.builder()
                .buildRound(eventDetails.created_by?.get(0)!!.toChar().toString(), color1)
        cd_image.background = drawable

        try {
            if(eventDetails.expiry_time != null) {
                val timeRemaining: Long = eventDetails.expiry_time!!.toLong() - AppUtils.getCurrentTimestamp().toLong()
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
                                tv_expire_time.setText(spannableStringBuilder)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }

                        override fun onFinish() {
                            tv_expire_time.setText("Expired")
                        }
                    }.start()
                } else {
                    tv_expire_time.setText("Expired")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun sendJoinReq(eventId:String){
        try {
            var joinReq = PollEvent(eventId)

            if (AppUtils.isNetworkConnected(this@EventDetailActivity)) {
//                var progressDialog  = ProgressDialog(context)
//                progressDialog.setMessage("Loading....")
//                progressDialog.show()
                AppUtils.showLoader(this@EventDetailActivity)

                val call = MyApplication.networkService.joinPollEvent(HeaderGenerator.createHeaderMap(this@EventDetailActivity),joinReq)
                call.enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>?) {
                        AppUtils.removeLoader(this@EventDetailActivity)
//                        progressDialog.dismiss()
                        if (response != null && response.body() != null && response.body().status.equals("OK")) {
                            getEventDetail(eventId)
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        AppUtils.removeLoader(this@EventDetailActivity)
//                        progressDialog.dismiss()
                        Log.e("errror", toString())
                        Toast.makeText(this@EventDetailActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@EventDetailActivity, "Network not connected!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun sendLeaveReq(eventId:String){
        try {
            val leaveReq = PollEvent(eventId)

            if (AppUtils.isNetworkConnected(this@EventDetailActivity)) {
//                var progressDialog  = ProgressDialog(context)
//                progressDialog.setMessage("Loading....")
//                progressDialog.show()

                AppUtils.showLoader(this@EventDetailActivity)

                val call = MyApplication.networkService.leavePollEvent(HeaderGenerator.createHeaderMap(this@EventDetailActivity),leaveReq)
                call.enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>?) {
//                        progressDialog.dismiss()
                        AppUtils.removeLoader(this@EventDetailActivity)
                        if (response != null && response.body() != null && response.body().status.equals("OK")) {
                            getEventDetail(eventId)
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
//                        progressDialog.dismiss()
                        AppUtils.removeLoader(this@EventDetailActivity)
                        Log.e("errror", toString())
                        Toast.makeText(this@EventDetailActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@EventDetailActivity, "Network not connected!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }


    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        rv_candidates.setHasFixedSize(true)
        rv_candidates.layoutManager = linearLayoutManager
        candidateDetailAdapter = CandidateDetailAdapter(this@EventDetailActivity , interestedCandidates)
        rv_candidates.adapter = candidateDetailAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        MainActivity().getAllPollData()
        EventBus.getDefault().post(GetPollDataEvent())
    }

}
