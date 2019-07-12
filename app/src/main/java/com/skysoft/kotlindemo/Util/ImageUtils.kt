package com.skysoft.kotlindemo.Util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast

import com.skysoft.kotlindemo.retrofit.ConstantApi

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


object ImageUtils {
    //保存图片到SD卡
    @Throws(IOException::class)
    fun saveFile(bm: Bitmap, context: Context, photoId: String) {
        val fileName = Environment.getExternalStorageDirectory().toString() + "/wanAndroid/"
        val imgName = "$photoId.jpg"
        val jia = File(fileName)              //新创的文件夹的名字
        if (!jia.exists()) {   //判断文件夹是否存在，不存在则创建
            jia.mkdirs()
        }
        val file = File("$jia/$imgName")
        val bos = BufferedOutputStream(FileOutputStream(file))
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos)

        bos.flush()
        bos.close()
        Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show()

    }

    fun setWallpaper(photoId: String, context: Activity) {
        val fileName = Environment.getExternalStorageDirectory().toString() + "/美女/" + photoId + ".jpg"
        val file = File(fileName)
        if (!file.exists()) {
            Toast.makeText(context, "请先下载图片", Toast.LENGTH_SHORT).show()
            return
        }
        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = FileInputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        val bitmap = BitmapFactory.decodeStream(fileInputStream)
        val intent = Intent(Intent.ACTION_ATTACH_DATA)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra("mimeType", "image/*")
        val uri = Uri.parse(MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, null, null))
        intent.data = uri
        context.startActivityForResult(intent, ConstantApi.SET_THE_WALLPAPER)
        //从相册选取照片设置为壁纸
        //        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        //        startActivity(Intent.createChooser(intent, "选择壁纸"));


        //利用WallpaparManager,添加权限set_wallpaper
        //        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        //        try {
        //            wallpaperManager.setBitmap(bitmap);
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }

    }
}
