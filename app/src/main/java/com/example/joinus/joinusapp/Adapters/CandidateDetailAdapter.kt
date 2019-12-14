package com.example.joinus.joinusapp.Adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.example.joinus.joinusapp.R
import com.example.joinus.joinusapp.models.CandidateModel
import kotlinx.android.synthetic.main.candidate_list_item.view.*
import kotlinx.android.synthetic.main.event_layout.view.cd_image

class CandidateDetailAdapter (var context : Context , var interestCandidates : List<CandidateModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CandidateDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.candidate_list_item , parent , false))
    }

    override fun getItemCount(): Int {
        return interestCandidates.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var candidateDetailViewHolder : CandidateDetailViewHolder = holder as CandidateDetailViewHolder
        val candidateModel : CandidateModel = interestCandidates.get(position)


        val generator : ColorGenerator = ColorGenerator.MATERIAL

        val color1 : Int = generator.getRandomColor()
        val drawable : TextDrawable = TextDrawable.builder()
                .buildRound(candidateModel.name?.get(0)!!.toChar().toString(), color1)
        candidateDetailViewHolder.cdImage.background = drawable

        candidateDetailViewHolder.tvCandidateName.text = candidateModel.name
        candidateDetailViewHolder.tvPhoneNumber.text = candidateModel.phone
    }

    inner class CandidateDetailViewHolder(view : View): RecyclerView.ViewHolder(view){

        var cdImage : CardView = view.cd_image
        var tvCandidateName : TextView = view.tv_candidate_name
        var tvPhoneNumber : TextView = view.tv_phone_number
    }
}