package com.evgeniykim.qreader3.presentation.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(histories[position])
    }

    override fun getItemCount(): Int = histories.size

    class ViewHolder(val containerView: View, private val onClick: (History, String) -> Unit):
    RecyclerView.ViewHolder(containerView){


        @RequiresApi(Build.VERSION_CODES.M)
        fun bindData(history: History) {
            with(history) {
                var rate: Int = position + 1
//                containerView.tvContext.text = "$rate. " + history.context
                containerView.tvContext.text = "$rate. " + history.context
                containerView.tvDate.text = history.date
//                containerView.ivShare.setOnClickListener { onClick(this, ActionEnums().ACTION_SHARE) }
//                containerView.ivCopy.setOnClickListener { onClick(this, ActionEnums().ACTION_COPY) }
//                containerView.ivSeacrh.setOnClickListener { onClick(this, ActionEnums().ACTION_SEARCH) }
                containerView.cardHis.setOnClickListener {
                    onClick(this, ActionEnums().ACTION_SEARCH)

                    Toast.makeText(containerView.context, "Opening browser", Toast.LENGTH_SHORT).show()
                    containerView.cardHis.setCardBackgroundColor(containerView.context.getColor(R.color.darkerGrey))
                }

            }
        }

    }

}