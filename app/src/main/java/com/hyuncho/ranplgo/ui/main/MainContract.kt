package com.hyuncho.ranplgo.ui.main

import android.content.Context
import com.hyuncho.ranplgo.ui.base.BasePresenter
import com.hyuncho.ranplgo.ui.base.BaseView

interface MainContract {
    interface View : BaseView {

    }

    interface Presenter :
        BasePresenter<View> {
        fun gotoRanking(context : Context)
        fun openMenuSelect(context:Context)
    }
}