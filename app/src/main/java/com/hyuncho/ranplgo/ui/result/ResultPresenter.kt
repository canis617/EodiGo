package com.hyuncho.ranplgo.ui.result

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.hyuncho.ranplgo.service.firebase.FireBaseDB
import com.hyuncho.ranplgo.service.firebase.GoogleLogin
import com.hyuncho.ranplgo.ui.main.MainActivity
import com.hyuncho.ranplgo.ui.ranking.RankingActivity
import com.hyuncho.ranplgo.utils.Location

class ResultPresenter : ResultContract.Presenter {

    private var resultView : ResultContract.View? = null
    private lateinit var firebaseDB : FireBaseDB
    private var score : Int = 0

    override fun attachView(view: ResultContract.View) {
        resultView = view
    }

    override fun detachView() {
        resultView = null
    }


    override fun getResultScore(startLocation: LatLng, selectedLocation: LatLng, range : Int) : Int{
        val location = Location(startLocation)
        score = location.getScore(selectedLocation,range)
        return score
    }

    override fun setScoreListText(
        resultList: ArrayList<LatLng>,
        selectedList: ArrayList<LatLng>,
        range: Int
    ) : Int{
        score = 0
        for(index in 0..(resultList.size-1)){
            var tempLocation : Location = Location(resultList.get(index))
            score += tempLocation.getScore(selectedList.get(index), range)
        }
        return score
    }

    override fun saveResultToFirebase(mode : String) {
        firebaseDB = FireBaseDB(GoogleLogin().getUserId())
        var hightScoreRef : DatabaseReference = firebaseDB.getHighScore(mode)
        hightScoreRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                Log.d("highscoredata", p0.toString())
                if(p0.value == null){
                    hightScoreRef.setValue(score)
                }
                else if(p0.value.toString().toInt() < score){
                    hightScoreRef.setValue(score)
                }

            }
        })
        firebaseDB.updateResult(mode, score)
    }

    override fun goto(context: Context, path: String) {
        var nextIntent = Intent(context, RankingActivity::class.java)
        if(path == "start"){
            nextIntent = Intent(context, MainActivity::class.java)
        }
        if(path == "ranking"){
            nextIntent = Intent(context, RankingActivity::class.java)
        }

        context.startActivity(nextIntent)
    }
}