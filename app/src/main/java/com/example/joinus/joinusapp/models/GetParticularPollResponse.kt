package com.example.joinus.joinusapp.models

import android.telecom.Call

data class GetParticularPollResponse(var status : String , var details: PollEvent , var interested_users : List<CandidateModel>){

}