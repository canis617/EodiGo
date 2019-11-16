package com.hyuncho.ranplgo.ui.result

class ResultPresenter : ResultContract.Presenter {

    private var resultView : ResultContract.View? = null

    override fun attachView(view: ResultContract.View) {
        resultView = view
    }

    override fun detachView() {
        resultView = null
    }

}