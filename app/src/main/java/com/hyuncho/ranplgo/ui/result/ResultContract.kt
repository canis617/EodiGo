package com.hyuncho.ranplgo.ui.result

import com.hyuncho.ranplgo.ui.base.BasePresenter
import com.hyuncho.ranplgo.ui.base.BaseView

interface ResultContract {
    interface View : BaseView {

    }

    interface Presenter :
        BasePresenter<View> {

    }
}