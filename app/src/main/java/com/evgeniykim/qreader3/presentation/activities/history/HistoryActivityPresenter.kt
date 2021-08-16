package com.evgeniykim.qreader3.presentation.activities.history

import android.content.Context
import com.evgeniykim.qreader3.data.orm.HistoryORM
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpPresenterImpl

open class HistoryActivityPresenter: BaseMvpPresenterImpl<HistoryActivityContract.View>(),
HistoryActivityContract.Presenter{

    private var historyORM: HistoryORM? = null

    override fun loadHistory(context: Context) {
        historyORM = HistoryORM()
        mView?.showHistory(historyORM!!.getAll(context))
    }


}