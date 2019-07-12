@file:Suppress("DEPRECATION")

package com.skysoft.kotlindemo.adapter

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.skysoft.kotlindemo.R
import com.skysoft.kotlindemo.bean.BelleBean
import com.skysoft.kotlindemo.bean.PictureBeanData
import com.skysoft.kotlindemo.bean.Result
import com.skysoft.kotlindemo.custom.RatioImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.belle_adapter_item.view.*
import java.security.Policy

class BelleAdapter(val context: Context,layoutResId: Int, data: MutableList<PictureBeanData>?) : BaseQuickAdapter<PictureBeanData, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: PictureBeanData?) {
        val imageView = helper!!.getView<RatioImageView>(R.id.photo_iv)
//        Glide.with(context).load(item!!.url)
//                .placeholder(R.mipmap.img_default_meizi)
//                .error(R.mipmap.ic_load_fail)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imageView)
        Log.i("=====",""+item!!.image_url)
        Picasso.with(context).load(item!!.image_url)
                .placeholder(R.mipmap.img_default_meizi)
                .error(R.mipmap.ic_load_fail)
                .into(imageView)
    }
}