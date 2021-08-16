package com.evgeniykim.qreader3.presentation.activities.splash


import com.evgeniykim.qreader3.presentation.mvp.BaseMvpPresenter
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpView

object SplashActivityContract {

    interface View : BaseMvpView {
        fun setupPermissions()
        fun startActivity()
        fun requestPermission()
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun permissionGranted()
        fun permissionDenied()
    }
}