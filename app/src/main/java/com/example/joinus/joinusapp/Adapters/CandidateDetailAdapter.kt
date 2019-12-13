package com.example.joinus.joinusapp.Adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.joinus.joinusapp.R
import kotlinx.android.synthetic.main.candidate_list_item.view.*
import kotlinx.android.synthetic.main.event_layout.view.cd_image

class CandidateDetailAdapter (var context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CandidateDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.candidate_list_item , parent , false))
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    inner class CandidateDetailViewHolder(view : View): RecyclerView.ViewHolder(view){

        var cdImage : CardView = view.cd_image
        var tvCandidateName : TextView = view.tv_candidate_name
        var tvPhoneNumber : TextView = view.tv_phone_number
    }
}