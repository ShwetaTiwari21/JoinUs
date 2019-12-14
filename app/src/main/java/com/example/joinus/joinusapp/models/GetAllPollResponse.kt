package com.example.joinus.joinusapp.models

/**
 * Created by shwetatiwari on 14/12/19.
 */

data class GetAllPollResponse(var status:String,var created: List<PollEvent>,var interested: List<PollEvent>,
                              var expiring_soon:List<PollEvent>) {

}