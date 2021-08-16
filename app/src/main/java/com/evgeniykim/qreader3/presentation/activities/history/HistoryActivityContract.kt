package com.evgeniykim.qreader3.presentation.activities.history

import android.content.Context
import com.evgeniykim.qreader3.domain.History
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpPresenter
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpView

object HistoryActivityContract {

    interface View: BaseMvpView {
        fun showHistory(histories: MutableList<History>)
    }

    interface Presenter: BaseMvpPresenter<View> {
        fun loadHistory(context: Context)
    }
}