package com.evgeniykim.qreader3.presentation.activities.Favorite

import android.content.Context
import com.evgeniykim.qreader3.domain.Favorite
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpPresenter
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpView

object FavoriteContract {
    interface View: BaseMvpView {
        fun showFavorite(favorites: MutableList<Favorite>)
    }

    interface Presenter: BaseMvpPresenter<View> {
        fun loadFavorites(context: Context)
    }
}