package com.hyuncho.ranplgo.ui.result

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.hyuncho.ranplgo.ui.base.BasePresenter
import com.hyuncho.ranplgo.ui.base.BaseView

interface ResultContract {
    interface View : BaseView {
        fun setScoreText(score:Int)
    }

    interface Presenter :
        BasePresenter<View> {
        fun getResultScore(startLocation: LatLng, selectedLocation: LatLng, range : Int) :Int
        fun saveResultToFirebase(mode : String)
        fun goto(context : Context, path : String)
        fun setScoreListText(resultList : ArrayList<LatLng>, selectedList :  ArrayList<LatLng>, range:Int) : Int
    }
}