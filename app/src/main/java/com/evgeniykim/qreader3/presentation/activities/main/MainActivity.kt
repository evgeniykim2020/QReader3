package com.evgeniykim.qreader3.presentation.activities.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.evgeniykim.qreader3.R
import com.evgeniykim.qreader3.data.orm.HistoryORM
import com.evgeniykim.qreader3.domain.History
import com.evgeniykim.qreader3.presentation.activities.history.HistoryActivity
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpActivity
import com.evgeniykim.qreader3.utils.Constants
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_scan_success.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.text.DateFormat
import java.util.*

class MainActivity : BaseMvpActivity<MainActivityContract.View, MainActivityContract.Presenter>(),
MainActivityContract.View, View.OnClickListener, ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null
    private var flashState: Boolean = false
    private var dialog: AlertDialog? = null
    private var mHistoryOrm: HistoryORM? = null


    override var mPresenter: MainActivityContract.Presenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mHistoryOrm = HistoryORM()
        initUI()
    }

    override fun onResume() {
        super.onResume()
        mScannerView?.setResultHandler(this)
        mScannerView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()
    }

    private fun initUI() {
        mScannerView = ZXingScannerView(this)
        frmContent.addView(mScannerView)
        btnLight.setOnClickListener(this)
        btnHistory.setOnClickListener(this)
        privacyPolicyTextView.setOnClickListener(this)
    }



    override fun showSuccessScanningDialog(result: String) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_scan_success, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        mDialogView.tvResult.text = result
        mDialogView.btnSearch.setOnClickListener { mPresenter.searchByResultBtnPressed(result) }
        mDialogView.btnCopy.setOnClickListener { mPresenter.copyResultBtnPressed(result) }
        mDialogView.btnShare.setOnClickListener { mPresenter.shareResultBtnPressed(result) }
        dialog = dialogBuilder.create()
        dialog?.setOnCancelListener { continueScanning() }
        dialog?.show()
    }

    override fun continueScanning() {
        dialog?.dismiss()
        mScannerView?.resumeCameraPreview(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btnLight -> {
                if (flashState) {
                    p0.setBackgroundResource(R.drawable.ic_flash_on)
                    showMessage(R.string.flashlight_turned_off)
                    mScannerView?.flash = false
                } else {
                    p0.setBackgroundResource(R.drawable.ic_baseline_flash_off_24)
                    showMessage(R.string.flashlight_turned_on)
                    mScannerView?.flash = true
                    flashState = true
                }
            }
            R.id.btnHistory -> {
                startActivity(Intent(this, HistoryActivity::class.java))
            }
            R.id.privacyPolicyTextView -> {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.privacyPolicy))
                startActivity(browserIntent)
            }
        }
    }

    override fun handleResult(p0: Result?) {
        var history: History = History(
            DateFormat.getDateTimeInstance().format(Calendar.getInstance().time),
            p0?.text.toString()
        )
        mHistoryOrm?.add(this, history)
        mPresenter.qrCodeScanner(history)
    }
}