package com.example.joinus.joinusapp.models

/**
 * Created by shwetatiwari on 14/12/19.
 */
data class PollEvent(var poll_title:String?, var poll_id:String?,var category: String?,var description:String?,
                     var created_by:String?, var interested_count:String?, var time:String?,var date:String?,var expiry_time:Number?,
                     var url:String?,var min_participants:String?, var max_participants:String?){

    constructor(poll_id: String):this(null,poll_id,null,null,null,null,null,null,null,null,null,null)

    constructor(poll_title: String,poll_id: String,category: String,description: String,time: String,date: String,
                url: String,min_participants: String,max_participants: String):this(poll_title,poll_id,category,description,
            null,null,time,date,null,url,min_participants,max_participants)
}