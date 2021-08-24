package com.evgeniykim.qreader3.presentation.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.evgeniykim.qreader3.R
import com.evgeniykim.qreader3.domain.Favorite
import com.evgeniykim.qreader3.domain.History
import com.evgeniykim.qreader3.utils.ActionEnums
import kotlinx.android.synthetic.main.adapter_favorite.view.*
import kotlinx.android.synthetic.main.adapter_history.view.*

class FavoriteAdapter(private val favorites: MutableList<Favorite>,
                      private val onClick: (Favorite, String) -> Unit) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    fun addFavorites(newFavorites: MutableList<Favorite>) {
        favorites.addAll(newFavorites)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.adapter_favorite, parent, false).let {
            ViewHolder(it, onClick)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(favorites[position])
    }

    override fun getItemCount(): Int = favorites.size

    class ViewHolder(val containerView: View, private val onClick: (Favorite, String) -> Unit):
        RecyclerView.ViewHolder(containerView){

        @RequiresApi(Build.VERSION_CODES.M)
        fun bindData(favorite: Favorite) {
            with(favorite) {
                var rate: Int = position + 1
                containerView.tvContextFav.text = "$rate. " + favorite.context
                containerView.tvDateFav.text = favorite.date
//                containerView.ivShareFav.setOnClickListener { onClick(this, ActionEnums().ACTION_SHARE) }
//                containerView.ivCopyFav.setOnClickListener { onClick(this, ActionEnums().ACTION_COPY) }
//                containerView.ivSeacrhFav.setOnClickListener { onClick(this, ActionEnums().ACTION_SEARCH) }
                containerView.cardFav.setOnClickListener {
                    onClick(this, ActionEnums().ACTION_SEARCH)
                    Toast.makeText(containerView.context, "Opening browser", Toast.LENGTH_SHORT).show()
                    containerView.cardFav.setCardBackgroundColor(containerView.context.getColor(R.color.darkerGrey))
                }

            }
        }

    }
}