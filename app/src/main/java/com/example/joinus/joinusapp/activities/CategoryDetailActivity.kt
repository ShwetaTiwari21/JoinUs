package com.example.joinus.joinusapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.joinus.joinusapp.Adapters.EventAdapter
import com.example.joinus.joinusapp.R
import com.example.joinus.joinusapp.models.PollEvent
import kotlinx.android.synthetic.main.activity_category_detail.*

class CategoryDetailActivity : AppCompatActivity() {
    lateinit var linearLayoutManager : LinearLayoutManager
    lateinit var eventAdapter: EventAdapter
    lateinit var pollEventList:List<PollEvent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detail)


        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        rv_event_list.setHasFixedSize(true)
        rv_event_list.layoutManager = linearLayoutManager
        eventAdapter = EventAdapter(this@CategoryDetailActivity,pollEventList)
        rv_event_list.adapter = eventAdapter
    }
}
