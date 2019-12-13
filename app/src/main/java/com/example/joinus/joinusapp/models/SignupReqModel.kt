package com.example.joinus.joinusapp.models

/**
 * Created by shwetatiwari on 13/12/19.
 */

    data class SignupReqModel(var username:String, var name:String?,var phone:String?){

    constructor(username: String) : this(username,null,null){

    }

}