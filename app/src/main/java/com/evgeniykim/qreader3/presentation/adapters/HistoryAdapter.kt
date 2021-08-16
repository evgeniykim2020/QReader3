package com.evgeniykim.qreader3.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.evgeniykim.qreader3.R
import com.evgeniykim.qreader3.domain.History
import com.evgeniykim.qreader3.utils.ActionEnums
import kotlinx.android.synthetic.main.adapter_history.view.*

class HistoryAdapter(private val histories: MutableList<History>,
private val onClick: (History, String) -> Unit) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    fun addHistories(newHistories: MutableList<History>) {
        histories.addAll(newHistories)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.adapter_history, parent, false).let {
            ViewHolder(it, onClick)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(histories[position])
    }

    override fun getItemCount(): Int = histories.size

    class ViewHolder(val containerView: View, private val onClick: (History, String) -> Unit):
    RecyclerView.ViewHolder(containerView){

        fun bindData(history: History) {
            with(history) {
                var rate: Int = position + 1
                containerView.tvContext.text = "$rate. " + history.context
                containerView.tvDate.text = history.date
                containerView.ivShare.setOnClickListener { onClick(this, ActionEnums().ACTION_SHARE) }
                containerView.ivCopy.setOnClickListener { onClick(this, ActionEnums().ACTION_COPY) }
                containerView.ivSeacrh.setOnClickListener { onClick(this, ActionEnums().ACTION_SEARCH) }

            }
        }

    }

}