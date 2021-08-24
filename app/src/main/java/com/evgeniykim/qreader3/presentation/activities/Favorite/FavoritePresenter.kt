package com.evgeniykim.qreader3.presentation.activities.Favorite

import android.content.Context
import com.evgeniykim.qreader3.data.orm.FavoriteORM
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpPresenterImpl

open class FavoritePresenter: BaseMvpPresenterImpl<FavoriteContract.View>(),
FavoriteContract.Presenter{

    private var favoriteORM: FavoriteORM? = null

    override fun loadFavorites(context: Context) {
        favoriteORM = FavoriteORM()
        mView?.showFavorite(favoriteORM!!.getAll(context))
    }



}