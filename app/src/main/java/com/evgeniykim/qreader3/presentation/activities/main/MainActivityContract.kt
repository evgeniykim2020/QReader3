package com.evgeniykim.qreader3.presentation.activities.main

import com.evgeniykim.qreader3.domain.Favorite
import com.evgeniykim.qreader3.domain.History
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpPresenter
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpView

object MainActivityContract {

    interface View: BaseMvpView {
        fun showSuccessScanningDialog(result: String)
        fun continueScanning()
    }

    interface Presenter: BaseMvpPresenter<View> {
        fun qrCodeScanner(history: History)
        fun searchByResultBtnPressed(result: String)
        fun copyResultBtnPressed(result: String)
        fun shareResultBtnPressed(result: String)
    }
}