package com.skysoft.kotlindemo.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.transition.Explode
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ganxin.library.LoadDataLayout
import com.skysoft.kotlindemo.R
import kotlinx.android.synthetic.main.activity_wan_android_details.*

class WanAndroidDetailsActivity : BaseActivity() {

    var url: String? = null
    override fun initView() {
        setSupportActionBar(toolbar)
        initViewSetting()
    }

    override fun setListern() {
        toolbar!!.setNavigationOnClickListener {
            finish()
        }
    }

    override fun initData() {
        url = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")
        toolbar_title.text = title
        supportActionBar!!.title=""
        initProgressWebViewData()
    }

    override fun getLayoutId(): Int {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.enterTransition = Explode()
        }
        return R.layout.activity_wan_android_details
    }


    @SuppressLint("SetJavaScriptEnabled")
     fun initViewSetting() {
        val settings = webView!!.settings
        settings.javaScriptEnabled = true
        webView!!.settings.defaultTextEncodingName = "UTF -8"//设置默认为utf-8
        //支持屏幕缩放
        settings.setSupportZoom(false)
        settings.builtInZoomControls = false
        //不显示webview缩放按钮
        settings.displayZoomControls = false
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        //settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initProgressWebViewData() {
        webView!!.loadUrl(url!!)
        webView!!.setDownloadListener { url, _, _, _, _ ->
            if (url != null && url.startsWith("http://"))
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)//在当前webview跳转新的url
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                loadDataLayout.status = LoadDataLayout.SUCCESS
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.exitTransition = Explode()
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            R.id.action_share -> share(url!!)
            R.id.action_copy_url -> copyText(url!!)
            R.id.action_browser_open -> browserOpenWeb(url)
            R.id.action_refresh -> initProgressWebViewData()
        }
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }
}
