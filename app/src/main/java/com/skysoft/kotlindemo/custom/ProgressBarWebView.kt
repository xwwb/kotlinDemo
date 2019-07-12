package com.skysoft.kotlindemo.custom

import android.content.Context
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.webkit.WebView
import android.widget.ProgressBar

import com.skysoft.kotlindemo.R

/**
 * Created by Administrator on 2017\8\22 0022.
 */

class ProgressBarWebView(context: Context, attrs: AttributeSet) : WebView(context, attrs) {
    private val progressbar: ProgressBar

    init {
        //创建以个横向的进度条
        progressbar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)

        //设置进度条的大小
        progressbar.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 1, 0, 0)

        //动态给进度条添加背景
        val d = ClipDrawable(ColorDrawable(resources.getColor(R.color.title_layout)), Gravity.LEFT, ClipDrawable.HORIZONTAL)
        progressbar.progressDrawable = d
        addView(progressbar)
        webChromeClient = WebChromeClient()
    }

    inner class WebChromeClient : android.webkit.WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (newProgress == 100) {
                progressbar.visibility = View.GONE
            } else {
                if (progressbar.visibility == View.GONE)
                    progressbar.visibility = View.VISIBLE
                progressbar.progress = newProgress
            }
            super.onProgressChanged(view, newProgress)
        }

    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val layoutParams = progressbar.layoutParams
        layoutParams.width=l
        layoutParams.height=t
        progressbar.layoutParams = layoutParams
        super.onScrollChanged(l, t, oldl, oldt)
    }
}
