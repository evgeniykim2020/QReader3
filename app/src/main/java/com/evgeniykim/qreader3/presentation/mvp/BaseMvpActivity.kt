package com.evgeniykim.qreader3.presentation.mvp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.evgeniykim.qreader3.R

abstract class BaseMvpActivity<in V : BaseMvpView, T : BaseMvpPresenter<V>> : AppCompatActivity(), BaseMvpView {

    protected abstract var mPresenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
    }

    override fun getContext(): Context = this

    override fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showError(stringResId: Int) {
        Toast.makeText(this, stringResId, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(strResId: Int) {
        Toast.makeText(this, strResId, Toast.LENGTH_SHORT).show()
    }

    override fun searchInWWW(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun copyToClipboard(value: String) {
        var clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var clip = ClipData.newPlainText("Qr-Code", value)
        clip = clipboard.primaryClip // Could be mistake

    }

    override fun shareResultViewSharingIntent(result: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.setType("text/plain")
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, result)
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)))
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


}