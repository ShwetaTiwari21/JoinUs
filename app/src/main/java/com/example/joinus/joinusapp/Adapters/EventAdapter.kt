package com.example.joinus.joinusapp.Adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.joinus.joinusapp.R
import kotlinx.android.synthetic.main.event_layout.view.*

class EventAdapter(var context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventViewHolder(LayoutInflater.from(context).inflate(R.layout.event_list_item , parent , false))
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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
}