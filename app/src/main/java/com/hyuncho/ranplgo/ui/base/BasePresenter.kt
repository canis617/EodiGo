package com.hyuncho.ranplgo.ui.base

interface BasePresenter<T> {

    fun attachView(view: T)
    fun detachView()

}