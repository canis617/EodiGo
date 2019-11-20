package com.hyuncho.ranplgo.data.user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyuncho.ranplgo.R

class ScoreAdapter (val context : Context, val userscoreList : ArrayList<UserScore>) : RecyclerView.Adapter<ScoreAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.components_score_list, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return userscoreList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(userscoreList[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val score_list_rank= itemView.findViewById<TextView>(R.id.score_list_rank)
        val score_list_id = itemView.findViewById<TextView>(R.id.score_list_id)
        val score_list_score = itemView.findViewById<TextView>(R.id.score_list_score)

        fun bind (userscore: UserScore, context: Context) {
            /* 나머지 TextView와 String 데이터를 연결한다. */
            score_list_rank?.text = (userscoreList.indexOf(userscore) + 1).toString()
            score_list_id?.text = userscore.getUser()
            score_list_score?.text = userscore.getScore()
        }
    }
}