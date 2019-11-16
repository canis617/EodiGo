package com.hyuncho.ranplgo.ui.ranking

import com.hyuncho.ranplgo.data.user.UserScore


class RankingPresenter : RankingContract.Presenter {

    private var rankingView : RankingContract.View? = null

    private var scoreList : ArrayList<UserScore> = ArrayList<UserScore>()

    override fun attachView(view: RankingContract.View) {
        rankingView = view
    }

    override fun detachView() {
        rankingView = null
    }

}