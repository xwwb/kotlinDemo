package com.skysoft.kotlindemo.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.skysoft.kotlindemo.R
import com.skysoft.kotlindemo.Util.ImageUtils
import com.skysoft.kotlindemo.retrofit.ApiService
import com.skysoft.kotlindemo.retrofit.ConstantApi
import com.skysoft.kotlindemo.retrofit.RetrofitUtils
import com.skysoft.kotlindemo.retrofit.SystemUiVisibilityUtil
import com.squareup.picasso.Picasso
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_picture.*
import okhttp3.ResponseBody
import java.io.IOException
import java.util.*

class PictureActivity : BaseActivity() {
     var mIsToolBarHidden: Boolean = false
     var mIsStatusBarHidden: Boolean = false
     var bitmap: Bitmap? = null
    var download_url:String?=null
    var share_url:String?=null
    var id:String?=null
    var image_url:String?=null
    override fun getLayoutId(): Int {
        return R.layout.activity_picture
    }

    @SuppressLint("RestrictedApi")
    override fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title=""
        toolbar.setTitle("妹纸")
    }

    override fun setListern() {
        toolbar.setNavigationOnClickListener { finish() }
        photo_touch_iv!!.setOnOutsidePhotoTapListener {
            hideOrShowToolbar()
            hideOrShowStatusBar()
        }
        photo_touch_iv!!.setOnPhotoTapListener { view, x, y ->
            hideOrShowToolbar()
            hideOrShowStatusBar()
        }

    }

    override fun initData() {
        download_url = intent.getStringExtra("download_url")
        share_url = intent.getStringExtra("share_url")
        id = intent.getStringExtra("id")
        image_url = intent.getStringExtra("image_url")
        Picasso.with(this).load(image_url).into(photo_touch_iv)
    }

    protected fun hideOrShowToolbar() {
        toolbar.animate()
                .alpha(if (mIsToolBarHidden) 1.0f else 0.0f)
                .setInterpolator(DecelerateInterpolator(2f))
                .start()
        mIsToolBarHidden = !mIsToolBarHidden
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_photo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            R.id.action_share -> share(share_url!!)
            R.id.action_save -> getPermission()
            R.id.action_set_wallpaper -> ImageUtils.setWallpaper(id!!, this)
        }
        return true
    }
    private fun hideOrShowStatusBar() {
        if (mIsStatusBarHidden) {
            SystemUiVisibilityUtil.enter(this)
        } else {
            SystemUiVisibilityUtil.exit(this)
        }
        mIsStatusBarHidden = !mIsStatusBarHidden
    }

     fun getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this, android.Manifest.permission.CAMERA)) {
                Toast.makeText(this, "请授权！", Toast.LENGTH_LONG).show()
                // 帮跳转到该应用的设置界面，让用户手动授权
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri1 = Uri.fromParts("package", packageName, null)
                intent.data = uri1
                startActivity(intent)
            } else {
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }
        } else {
            savePhoto()

        }
    }

    private fun savePhoto() {
        RetrofitUtils.getInstance(this, ConstantApi.BaiDuPictureUrl)!!.create<ApiService>(ApiService::class.java).downloadPicFromNet(download_url!!).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseBody> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(responseBody: ResponseBody) {

                        Log.i("=====responseBody", "responseBody")
                        try {
                            val bytes = responseBody.bytes()
                            Log.i("====byte", "" + bytes.size)
                            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                                ImageUtils.saveFile(bitmap!!,this@PictureActivity, id!!)

                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })


    }
}
