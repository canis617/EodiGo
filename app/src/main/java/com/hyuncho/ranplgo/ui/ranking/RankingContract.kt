package com.hyuncho.ranplgo.ui.ranking

import com.hyuncho.ranplgo.ui.base.BasePresenter
import com.hyuncho.ranplgo.ui.base.BaseView

interface RankingContract {
    interface View : BaseView {

    }

    interface Presenter :
        BasePresenter<View> {

    }
}