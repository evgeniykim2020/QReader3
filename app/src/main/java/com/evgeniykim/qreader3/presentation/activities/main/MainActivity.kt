package com.evgeniykim.qreader3.presentation.activities.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import com.evgeniykim.qreader3.R
import com.evgeniykim.qreader3.data.orm.FavoriteORM
import com.evgeniykim.qreader3.data.orm.HistoryORM
import com.evgeniykim.qreader3.domain.Favorite
import com.evgeniykim.qreader3.domain.History
import com.evgeniykim.qreader3.presentation.activities.Favorite.FavoriteActivity
import com.evgeniykim.qreader3.presentation.activities.history.HistoryActivity
import com.evgeniykim.qreader3.presentation.activities.settings.SettingActivity
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpActivity
import com.evgeniykim.qreader3.utils.Constants
import com.evgeniykim.qreader3.utils.UpperButtonsColors
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_scan_success.*
import kotlinx.android.synthetic.main.dialog_scan_success.view.*
import kotlinx.android.synthetic.main.toolbar.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.text.DateFormat
import java.util.*

class MainActivity : BaseMvpActivity<MainActivityContract.View, MainActivityContract.Presenter>(),
MainActivityContract.View, View.OnClickListener, ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null
    private var flashState: Boolean = false
    private var dialog: AlertDialog? = null
    private var mHistoryOrm: HistoryORM? = null
    private var mFavoriteORM: FavoriteORM? = null
    private var favorite: Favorite? = null

    var buttonsColors: UpperButtonsColors = UpperButtonsColors()

    override var mPresenter: MainActivityContract.Presenter = MainActivityPresenter()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBarMain)

        currentActivityBtnColors()

        mHistoryOrm = HistoryORM()
        mFavoriteORM = FavoriteORM()
        initUI()
        btnLight.visibility = View.VISIBLE

        equalBtns()

    }

    override fun onResume() {
        super.onResume()
        mScannerView?.setResultHandler(this)
        mScannerView?.startCamera()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()
    }

    private fun initUI() {
        mScannerView = ZXingScannerView(this)
        frmContent.addView(mScannerView)
        btnLight.setOnClickListener(this)
        btnHistory1.setOnClickListener(this)
        btnFav1.setOnClickListener(this)
        btnSet1.setOnClickListener(this)

        // Focusing on imagebuttons
        btnHistory.setOnClickListener(this)
        btnFav.setOnClickListener(this)
        btnSet.setOnClickListener(this)

    }

    private fun equalBtns() {
        if (btnScan.isPressed) {
            btnScan1.isPressed
        }
        if (btnFav.isPressed) {
            btnFav1.isPressed
        }
    }



    override fun showSuccessScanningDialog(result: String) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_scan_success, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        mDialogView.tvResult.text = result
        mDialogView.btnSearch.setOnClickListener { mPresenter.searchByResultBtnPressed(result) }
        mDialogView.btnCopy.setOnClickListener { mPresenter.copyResultBtnPressed(result) }
        mDialogView.btnShare.setOnClickListener { mPresenter.shareResultBtnPressed(result) }
        mDialogView.btnFavorite.setOnClickListener {
            addFav(result)
            Toast.makeText(this, "Added to Fav", Toast.LENGTH_SHORT).show()
            dialog?.hide()
            startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }
        dialog = dialogBuilder.create()
        dialog?.setOnCancelListener { continueScanning() }
        dialog?.show()
    }

    override fun continueScanning() {
        dialog?.dismiss()
        mScannerView?.resumeCameraPreview(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btnLight -> {
                if (flashState) {
//                    p0.setBackgroundResource(R.drawable.ic_flash_on)
                    showMessage(R.string.flashlight_turned_off)
                    mScannerView?.flash = false
//                    buttonsColors.buttonUnpressed(this, btnLight, txtLight, viewLineLight)
//                    buttonsColors.buttonPressed(this, btnScan, txtScan, viewLineScan)

                } else {
//                    p0.setBackgroundResource(R.drawable.ic_baseline_flash_off_24)
                    showMessage(R.string.flashlight_turned_on)
                    mScannerView?.flash = true
                    flashState = true
//                    rootBtnColors()
//                    buttonsColors.buttonPressed(this, btnLight, txtLight, viewLineLight)
                }
            }
            R.id.btnHistory1 -> {
                buttonsColors.buttonPressed(this, btnHistory, txtHistory, viewLineHistory)
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
                finish()
            }

            R.id.btnFav1 -> {
                buttonsColors.buttonPressed(this, btnFav, txtFav, lineViewFav)
                val intent = Intent(this, FavoriteActivity::class.java)
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

            // Focusing on imagebuttons


            R.id.btnHistory -> {
                buttonsColors.buttonPressed(this, btnHistory, txtHistory, viewLineHistory)
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
                finish()
            }

            R.id.btnFav -> {
                buttonsColors.buttonPressed(this, btnFav, txtFav, lineViewFav)
                val intent = Intent(this, FavoriteActivity::class.java)
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

    override fun handleResult(p0: Result?) {
        val history = History(
            DateFormat.getDateTimeInstance().format(Calendar.getInstance().time),
            p0?.text.toString()
        )
        mHistoryOrm?.add(this, history)
        mPresenter.qrCodeScanner(history)

    }

    private fun addFav(result: String) {

        val fav = Favorite(
            DateFormat.getDateTimeInstance().format(Calendar.getInstance().time),
            result
        )
        mFavoriteORM?.add(this, fav)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun rootBtnColors() {
        buttonsColors.buttonUnpressed(this, btnScan, txtScan, viewLineScan)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun currentActivityBtnColors() {
        buttonsColors.buttonPressed(this, btnScan, txtScan, viewLineScan)
        lightId.visibility = View.VISIBLE
    }
}