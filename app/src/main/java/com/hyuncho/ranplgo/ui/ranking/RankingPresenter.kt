package com.hyuncho.ranplgo.ui.ranking

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.hyuncho.ranplgo.data.user.ScoreAdapter
import com.hyuncho.ranplgo.data.user.UserScore
import com.hyuncho.ranplgo.service.firebase.FireBaseDB


class RankingPresenter : RankingContract.Presenter {

    private var rankingView : RankingContract.View? = null

    private var scoreList : ArrayList<UserScore> = ArrayList<UserScore>()
    private var currentStatus : String = "guesser"
    private var firebaseDB : FireBaseDB = FireBaseDB(FirebaseAuth.getInstance().currentUser)

    override fun attachView(view: RankingContract.View) {
        rankingView = view
    }

    override fun detachView() {
        rankingView = null
    }

    override fun getRankingList(context: Context){
        if(scoreList.size>0){
            val newList = ArrayList<UserScore>()
            this.scoreList = newList
        }

        rankingView?.setDisplay(currentStatus)

        val resultRef : DatabaseReference = firebaseDB.getHighscoreDataRef(currentStatus)
        resultRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children) {
                    scoreList.add(UserScore(snapshot.key.toString(), snapshot.value.toString()))
                }
                Log.d("scorelist", scoreList.toString())
                scoreList.sort()
                rankingView?.setRecyclerView(ScoreAdapter(context, scoreList))
            }
        })
    }

    override fun setStatus(context: Context, status: String) {
        currentStatus = status
        getRankingList(context)
    }

}