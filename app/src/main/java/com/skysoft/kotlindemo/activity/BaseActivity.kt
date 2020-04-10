package com.skysoft.kotlindemo.activity

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.gyf.barlibrary.ImmersionBar
import com.skysoft.kotlindemo.R
import com.skysoft.kotlindemo.retrofit.RetrofitUtils

abstract class BaseActivity : AppCompatActivity() {
    var immersionBar: ImmersionBar? = null
    private var toast: Toast? = null
    var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        context = this
        val networkAvailable = RetrofitUtils.isNetworkAvailable(this)
        if (!networkAvailable) {
            Toast.makeText(context, resources.getString(R.string.network_error), Toast.LENGTH_SHORT).show()
        }
        setStatusBar(this)
        initView()
        initData()
        setListern()
    }


    protected abstract fun getLayoutId(): Int
    protected abstract fun initView()


    protected abstract fun setListern()


    protected abstract fun initData()

    /*
        解决所有版本的沉浸栏
         */
    fun setStatusBar(activity: Activity) {
        immersionBar = ImmersionBar.with(activity)
        immersionBar!!.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (immersionBar != null) {
            immersionBar!!.destroy()
        }
        context = null
    }

    fun share(url: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share))
        intent.putExtra(Intent.EXTRA_TEXT, getShareContents(url))
        startActivity(Intent.createChooser(intent, title))
    }

    private fun getShareContents(url: String?): String {
        return getString(R.string.share_contents, "", url)
    }

    fun browserOpenWeb(url: String?) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    fun copyText(url: String) {
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val mClipData = ClipData.newPlainText("Label", url)
        cm.primaryClip = mClipData
        Toast.makeText(context, getString(R.string.copy_success), Toast.LENGTH_SHORT).show()
    }
}
