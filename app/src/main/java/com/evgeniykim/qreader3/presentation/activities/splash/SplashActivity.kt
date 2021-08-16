package com.evgeniykim.qreader3.presentation.activities.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.evgeniykim.qreader3.R
import com.evgeniykim.qreader3.presentation.activities.main.MainActivity
import com.evgeniykim.qreader3.presentation.mvp.BaseMvpActivity

class SplashActivity: BaseMvpActivity<SplashActivityContract.View, SplashActivityContract.Presenter>(),
SplashActivityContract.View {

    private val RECORD_REQUEST_CODE = 101


    override var mPresenter: SplashActivityContract.Presenter = SplashActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupPermissions()
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
        startActivity(Intent(this, MainActivity::class.java))
        finish()
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


}