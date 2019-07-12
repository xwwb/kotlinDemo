package com.skysoft.kotlindemo.Util

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView

import com.squareup.picasso.Picasso
import com.youth.banner.loader.ImageLoader

/**
 * Created by Administrator on 2017\8\30 0030.
 */

class PicassoImage : ImageLoader {

    constructor() {}

    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        val s = path.toString()

        if (!TextUtils.isEmpty(s)) {
            Picasso.with(context).load(path.toString()).into(imageView)
        }

    }
}
