package com.hyuncho.ranplgo.ui.ranking

import android.content.Context
import com.hyuncho.ranplgo.data.user.ScoreAdapter
import com.hyuncho.ranplgo.ui.base.BasePresenter
import com.hyuncho.ranplgo.ui.base.BaseView

interface RankingContract {
    interface View : BaseView {
        fun setRecyclerView(scoreAdapter: ScoreAdapter)
        fun setDisplay(status : String)
    }

    interface Presenter :
        BasePresenter<View> {
        fun getRankingList(context: Context)
        fun setStatus(context: Context, status : String)

    }
}