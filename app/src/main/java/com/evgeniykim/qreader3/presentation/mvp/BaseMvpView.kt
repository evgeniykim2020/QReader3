package com.evgeniykim.qreader3.presentation.mvp

import android.content.Context
import androidx.annotation.StringRes

interface BaseMvpView {

    fun getContext(): Context

    fun showError(error: String?)

    fun showError(@StringRes stringResId: Int)

    fun showMessage(@StringRes strResId: Int)

    fun showMessage(message: String)

    fun searchInWWW(url: String)

    fun copyToClipboard(value: String)

    fun shareResultViewSharingIntent(result: String)


}