package com.example.joinus.joinusapp.models

/**
 * Created by shwetatiwari on 14/12/19.
 */
data class PollEvent(var poll_title:String?, var poll_id:String?,var category: String?,var description:String?,
                     var created_by:String?, var interested_count:String?, var expiry_time:Number?,
                     var url:String?,var min_participants:String?, var max_participants:String? , var is_interested : Boolean){

    constructor(poll_id: String):this(null,poll_id,null,null,null,null,null,null,null,null ,false)
}