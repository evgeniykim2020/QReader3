package com.evgeniykim.qreader3.utils

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.evgeniykim.qreader3.R
import kotlinx.android.synthetic.main.toolbar.*

class UpperButtonsColors {

    @RequiresApi(Build.VERSION_CODES.M)
    fun buttonPressed(context: Context, button: ImageButton, text: TextView, viewLine: View) {
        button.setBackgroundColor(context.resources.getColor(R.color.blueLight))
        button.setColorFilter(context.getColor(R.color.colorPrimaryDark))
        button.background = context.resources.getDrawable(R.drawable.circle_button_top)
        text.setTextColor(context.resources.getColor(R.color.blueLight))
        viewLine.setBackgroundColor(context.resources.getColor(R.color.blueLight))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun buttonUnpressed(context: Context, button: ImageButton, text: TextView, viewLine: View) {
        button.setColorFilter(context.getColor(R.color.lightgrey))
        button.setBackgroundColor(context.resources.getColor(R.color.colorPrimaryDark))
        button.background = context.resources.getDrawable(R.drawable.circle_button_top_unpressed)
        text.setTextColor(context.resources.getColor(R.color.lightgrey))
        viewLine.setBackgroundColor(context.resources.getColor(R.color.colorPrimaryDark))
    }

}