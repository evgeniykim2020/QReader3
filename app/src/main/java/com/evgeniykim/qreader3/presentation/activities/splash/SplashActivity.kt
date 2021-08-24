package com.evgeniykim.qreader3.presentation.activities.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.evgeniykim.qreader3.R
import com.evgeniykim.qreader3.presentation.activities.main.MainActivity
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity: BaseMvpActivity<SplashActivityContract.View, SplashActivityContract.Presenter>(),
SplashActivityContract.View {

    private val RECORD_REQUEST_CODE = 101

    private var progressStatus = 0

    private var handler = Handler()

    private val permissionListener: BasePermissionListener? = null

    override var mPresenter: SplashActivityContract.Presenter = SplashActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        btnStart.setOnClickListener {
            setupPermissions()
            btnStart.visibility = View.GONE
            terms1.visibility = View.GONE
            termsService.visibility = View.GONE
        }

        // Open privacy and terms
        policyPrivacy.setOnClickListener {
            val uri = Uri.parse("https://sites.google.com/antixyz.ventures/qrscannerpp/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            policyPrivacy.setTextColor(resources.getColor(R.color.colorPrimary))
            startActivity(intent)
        }

        // Open terms
        termsService.setOnClickListener {
            val uri = Uri.parse("https://sites.google.com/antixyz.ventures/termsqrcodescanner/")
            termsService.setTextColor(resources.getColor(R.color.colorPrimary))
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }


    }

    override fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermission()
        } else {
            mPresenter.permissionGranted()
        }

    }

    override fun startActivity() {

        progressBarStart()

//        startActivity(Intent(this, MainActivity::class.java))
//        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
        RECORD_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    mPresenter.permissionDenied()
                } else {
                    mPresenter.permissionGranted()


                }
            }
        }
    }

    private fun progressBarStart() {

        splash_progress.visibility = View.VISIBLE

        Thread(Runnable {
            while (progressStatus < 100) {
                progressStatus += 2

                Thread.sleep(40)

                handler.post {
                    splash_progress.progress = progressStatus


                }


            }

            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }).start()


    }

}