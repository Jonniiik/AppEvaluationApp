package com.example.appevaluation

import android.content.Context
import android.widget.Toast

/**
 * Class for visual Toast for example in Services
 */
class DisplayToast(private val mContext: Context, var mText: String) :
    Runnable {
    override fun run() {
        Toast.makeText(mContext, mText, Toast.LENGTH_SHORT).show()
    }
}