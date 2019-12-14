package com.example.joinus.joinusapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.joinus.joinusapp.Adapters.CandidateDetailAdapter
import com.example.joinus.joinusapp.Adapters.EventAdapter
import com.example.joinus.joinusapp.MyApplication
import com.example.joinus.joinusapp.R
import com.example.joinus.joinusapp.models.GetAllPollResponse
import com.example.joinus.joinusapp.utils.AppUtils
import com.example.joinus.joinusapp.utils.Const
import kotlinx.android.synthetic.main.activity_category_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventDetailActivity : AppCompatActivity() {

    var event_id : String? = null
    lateinit var linearLayoutManager : LinearLayoutManager
    lateinit var candidateDetailAdapter: CandidateDetailAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        if(intent != null) {
            event_id = intent.getStringExtra(Const.EVENT_ID)
        }
        getEventDetail(event_id)
    }

    private fun getEventDetail(eventId: String?) {
        try {

        }catch (e : Exception){
            e.printStackTrace()
        }

    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        rv_event_list.setHasFixedSize(true)
        rv_event_list.layoutManager = linearLayoutManager
        candidateDetailAdapter = CandidateDetailAdapter(this@EventDetailActivity)
        rv_event_list.adapter = candidateDetailAdapter
    }
}
