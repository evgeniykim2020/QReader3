package com.evgeniykim.qreader3.presentation.activities.settings

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.evgeniykim.qreader3.R
import com.evgeniykim.qreader3.presentation.activities.Favorite.FavoriteActivity
import com.evgeniykim.qreader3.presentation.activities.history.HistoryActivity
import com.evgeniykim.qreader3.presentation.activities.main.MainActivity
import com.evgeniykim.qreader3.utils.UpperButtonsColors
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.toolbar.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    private var mScannerView: ZXingScannerView? = null
    private var flashState: Boolean = false
    var buttonsColors: UpperButtonsColors = UpperButtonsColors()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initUI()
        setColorsPressed()
        btnLightBlocked.visibility = View.VISIBLE

        // Open privacy and terms
        privacy.setOnClickListener {
            val uri = Uri.parse("https://sites.google.com/antixyz.ventures/qrscannerpp/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            privacyTxt.setTextColor(resources.getColor(R.color.blueLight))
            startActivity(intent)
             }

        // Open terms
        termsLink.setOnClickListener {
            val uri = Uri.parse("https://sites.google.com/antixyz.ventures/termsqrcodescanner/")
            termsTxt.setTextColor(resources.getColor(R.color.blueLight))
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setColorsPressed(){
        buttonsColors.buttonPressed(this, btnSet, txtSet, viewLineSet)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun rootBtnColors(){
        buttonsColors.buttonUnpressed(this, btnSet, txtSet, viewLineSet)
    }


    private fun initUI() {
        btnScan1.setOnClickListener(this)
        btnFav1.setOnClickListener(this)
        btnHistory1.setOnClickListener(this)

        // Imagebuttons
        btnScan.setOnClickListener(this)
        btnFav.setOnClickListener(this)
        btnHistory.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(p0: View?) {
        when(p0?.id) {


            R.id.btnFav1 -> {
                buttonsColors.buttonPressed(this, btnFav, txtFav, lineViewFav)
                val intent = Intent(this, FavoriteActivity::class.java)
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
            R.id.btnHistory1 -> {
                buttonsColors.buttonPressed(this, btnHistory, txtHistory, viewLineHistory)
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
                finish()
            }

            // Imagebuttons

            R.id.btnFav -> {
                buttonsColors.buttonPressed(this, btnFav, txtFav, lineViewFav)
                val intent = Intent(this, FavoriteActivity::class.java)
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
            R.id.btnHistory -> {
                buttonsColors.buttonPressed(this, btnHistory, txtHistory, viewLineHistory)
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
                finish()
            }
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}