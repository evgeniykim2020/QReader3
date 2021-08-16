package com.evgeniykim.qreader3.presentation.activities.history

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeniykim.qreader3.R
import com.evgeniykim.qreader3.domain.History
import com.evgeniykim.qreader3.presentation.adapters.HistoryAdapter
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpActivity
import com.evgeniykim.qreader3.utils.ActionEnums
import com.evgeniykim.qreader3.utils.Constants.preUrl
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity: BaseMvpActivity<HistoryActivityContract.View, HistoryActivityContract.Presenter>(),
HistoryActivityContract.View {

    private var mAdapter: HistoryAdapter? = null

    override var mPresenter: HistoryActivityContract.Presenter = HistoryActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setUpRecyclerView()
        mPresenter.loadHistory(this)
    }

    override fun showHistory(histories: MutableList<History>) {
        mAdapter?.addHistories(histories)
        mAdapter?.notifyDataSetChanged()
    }

    private fun setUpRecyclerView() {
        mAdapter = HistoryAdapter(ArrayList<History>()) {
            history, action ->
            when(action) {
                ActionEnums().ACTION_SEARCH -> {
                    searchInWWW(preUrl + history.context)
                }
                ActionEnums().ACTION_SHARE -> {
                    shareResultViewSharingIntent(history.context)
                }
                ActionEnums().ACTION_COPY -> {
                    copyToClipboard(history.context)
                }
            }
        }
        rvHistory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvHistory.adapter = mAdapter
    }


}