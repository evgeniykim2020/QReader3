package com.evgeniykim.qreader3.presentation.activities.Favorite

import android.app.ActivityOptions
import android.content.Intent
import android.icu.text.CaseMap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeniykim.qreader3.R
import com.evgeniykim.qreader3.data.orm.FavoriteORM
import com.evgeniykim.qreader3.data.orm.HistoryORM
import com.evgeniykim.qreader3.domain.Favorite
import com.evgeniykim.qreader3.presentation.activities.history.HistoryActivity
import com.evgeniykim.qreader3.presentation.activities.main.MainActivity
import com.evgeniykim.qreader3.presentation.activities.settings.SettingActivity
import com.evgeniykim.qreader3.presentation.adapters.FavoriteAdapter
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpActivity
import com.evgeniykim.qreader3.utils.ActionEnums
import com.evgeniykim.qreader3.utils.Constants
import com.evgeniykim.qreader3.utils.UpperButtonsColors
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_scan_success.*
import kotlinx.android.synthetic.main.toolbar.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class FavoriteActivity : BaseMvpActivity<FavoriteContract.View, FavoriteContract.Presenter>(),
    FavoriteContract.View, View.OnClickListener {

    private var mAdapter: FavoriteAdapter? = null
    private lateinit var mFavoriteORM: FavoriteORM
    override var mPresenter: FavoriteContract.Presenter = FavoritePresenter()
    private val upperButtonsColors: UpperButtonsColors = UpperButtonsColors()
    private var mScannerView: ZXingScannerView? = null
    private var flashState: Boolean = false
    var buttonsColors: UpperButtonsColors = UpperButtonsColors()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setSupportActionBar(toolBarMain)

        setUpRecyclerView()
        mPresenter.loadFavorites(this)

//        btnBackFav.setOnClickListener {
//            onBackPressed()
//        }
//
//        btnClearFav.setOnClickListener {
//            mFavoriteORM = FavoriteORM()
//            mFavoriteORM.clearAll(this)
//            Toast.makeText(this, "All cleared", Toast.LENGTH_SHORT).show()
//
//            this.recreate()
//        }

//        btnFav.setBackgroundColor(resources.getColor(R.color.blueLight))
//        btnFav.setColorFilter(R.color.colorPrimaryDark)
//        btnFav.background = resources.getDrawable(R.drawable.circle_button_top)
//        txtFav.setTextColor(resources.getColor(R.color.blueLight))
//        lineViewFav.setBackgroundColor(resources.getColor(R.color.blueLight))

        setColorsPressed()
        initUI()
        btnLightBlocked.visibility = View.VISIBLE


    }

    override fun showFavorite(favorites: MutableList<Favorite>) {
        mAdapter?.addFavorites(favorites)
        mAdapter?.notifyDataSetChanged()
    }

    private fun setUpRecyclerView() {
        mAdapter = FavoriteAdapter(ArrayList<Favorite>()) {
                favorite, action ->
            when(action) {
                ActionEnums().ACTION_SEARCH -> {
                    searchInWWW(Constants.preUrl + favorite.context)
                }
                ActionEnums().ACTION_SHARE -> {
                    shareResultViewSharingIntent(favorite.context)
                }
                ActionEnums().ACTION_COPY -> {
                    copyToClipboard(favorite.context)
                }
            }
        }
        rvFavorite.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvFavorite.adapter = mAdapter
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setColorsPressed(){
        upperButtonsColors.buttonPressed(this, btnFav, txtFav, lineViewFav)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun rootBtnColors(){
        upperButtonsColors.buttonUnpressed(this, btnFav, txtFav, lineViewFav)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(p0: View) {
        when(p0.id) {

            R.id.btnHistory1 -> {
                buttonsColors.buttonPressed(this, btnHistory, txtHistory, viewLineHistory)
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
                finish()

            }
            R.id.btnScan1 -> {
                buttonsColors.buttonPressed(this, btnScan, txtScan, viewLineScan)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
                finish()

            }
            R.id.btnSet1 -> {
                buttonsColors.buttonPressed(this, btnSet, txtSet, viewLineSet)
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
                finish()
            }

            // imagebuttons
            R.id.btnHistory -> {
                buttonsColors.buttonPressed(this, btnHistory, txtHistory, viewLineHistory)
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
                finish()

            }
            R.id.btnScan -> {
                buttonsColors.buttonPressed(this, btnScan, txtScan, viewLineScan)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
                finish()

            }
            R.id.btnSet -> {
                buttonsColors.buttonPressed(this, btnSet, txtSet, viewLineSet)
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
                finish()
            }

        }
    }

    private fun initUI() {
        btnHistory1.setOnClickListener(this)
        btnScan1.setOnClickListener(this)
        btnSet1.setOnClickListener(this)

        btnHistory.setOnClickListener(this)
        btnScan.setOnClickListener(this)
        btnSet.setOnClickListener(this)
    }


}