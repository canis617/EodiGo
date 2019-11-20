package com.hyuncho.ranplgo.ui.main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import com.hyuncho.ranplgo.ui.menuSelect.MenuSelectDialog
import com.hyuncho.ranplgo.ui.ranking.RankingActivity

class MainPresenter : MainContract.Presenter {

    private var mainView : MainContract.View? = null

    override fun attachView(view: MainContract.View) {
        mainView = view
    }

    override fun detachView() {
        mainView = null
    }

    override fun gotoRanking(context : Context) {
        val nextIntent = Intent(context, RankingActivity::class.java)
        context.startActivity(nextIntent)
    }

    override fun openMenuSelect(context: Context) {
        val menuSelectDialog = AlertDialog.Builder(context)
        val mView= MenuSelectDialog(context).getView()

        menuSelectDialog.setView(mView)
        menuSelectDialog.create()
        menuSelectDialog.show()
    }
}