package com.evgeniykim.qreader3.presentation.mvp

interface BaseMvpPresenter<in V : BaseMvpView> {

    fun attachView(view: V)

    fun detachView()
}