package com.hyuncho.ranplgo.ui.main

class MainPresenter : MainContract.Presenter {

    private var mainView : MainContract.View? = null

    override fun attachView(view: MainContract.View) {
        mainView = view
    }

    override fun detachView() {
        mainView = null
    }

}