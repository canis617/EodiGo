package com.hyuncho.ranplgo.ui.main

import com.hyuncho.ranplgo.ui.base.BasePresenter
import com.hyuncho.ranplgo.ui.base.BaseView

interface MainContract {
    interface View : BaseView {

    }

    interface Presenter :
        BasePresenter<View> {

    }
}