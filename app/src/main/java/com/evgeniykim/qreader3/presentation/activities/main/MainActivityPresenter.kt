package com.evgeniykim.qreader3.presentation.activities.main

import android.util.Patterns
import com.evgeniykim.qreader3.R
import com.evgeniykim.qreader3.domain.Favorite
import com.evgeniykim.qreader3.domain.History
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpPresenterImpl

open class MainActivityPresenter: BaseMvpPresenterImpl<MainActivityContract.View>(),
MainActivityContract.Presenter {

    private val preUrl: String = "https://www.google.com/#q="

    override fun qrCodeScanner(history: History) {
        mView?.showSuccessScanningDialog(history.context)
    }

    override fun searchByResultBtnPressed(result: String) {
        var url: String = result
        if (!Patterns.WEB_URL.matcher(result).matches()) {
            url = preUrl + result
        }
        mView?.continueScanning()
        mView?.searchInWWW(url)
    }

    override fun copyResultBtnPressed(result: String) {
        mView?.copyToClipboard(result)
        mView?.continueScanning()
        mView?.showMessage(R.string.text_copied)
    }

    override fun shareResultBtnPressed(result: String) {
        mView?.continueScanning()
        mView?.shareResultViewSharingIntent(result)
    }


}