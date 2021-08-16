package com.evgeniykim.qreader3.presentation.activities.splash

import com.evgeniykim.qreader3.presentation.mvp.BaseMvpPresenterImpl

open class SplashActivityPresenter: BaseMvpPresenterImpl<SplashActivityContract.View>(),
SplashActivityContract.Presenter {


    override fun permissionGranted() {
        mView?.startActivity()
    }

    override fun permissionDenied() {
        mView?.setupPermissions()
    }

}