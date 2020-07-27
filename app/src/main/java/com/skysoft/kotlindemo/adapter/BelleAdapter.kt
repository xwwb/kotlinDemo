@file:Suppress("DEPRECATION")

package com.skysoft.kotlindemo.adapter

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.skysoft.kotlindemo.R
import com.skysoft.kotlindemo.bean.GankModel
import com.skysoft.kotlindemo.bean.GankModelData
import com.skysoft.kotlindemo.custom.RatioImageView
import com.squareup.picasso.Picasso

class BelleAdapter(val context: Context, layoutResId: Int, data: MutableList<GankModelData>?) : BaseQuickAdapter<GankModelData, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: GankModelData?) {
        val imageView = helper!!.getView<RatioImageView>(R.id.photo_iv)
        val describeTextView = helper.getView<TextView>(R.id.describe_tv)
        val expectTextView = helper.getView<TextView>(R.id.expect)
        val authorTextView = helper.getView<TextView>(R.id.author_tv)
        val createdAtTextView = helper.getView<TextView>(R.id.createdAt_tv)
        createdAtTextView.text=item?.createdAt
        val expect = "妹纸图" + item?.title
        expectTextView.text=expect
        authorTextView.text=item?.author
        describeTextView.text=item?.desc?.trim()?.replace("\n","")
        val images = item?.images
        val s = images?.get(0)
        Log.i("BelleAdapter:","第几期:"+item?.title+"author:"+item?.author+"desc"+item?.desc+item?.createdAt)
        Glide.with(context).load(item!!.url)
                .placeholder(R.mipmap.img_default_meizi)
                .error(R.mipmap.ic_load_fail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        Log.i("=====", "" + item!!.url)
//        Picasso.with(context).load(item.url)
//                .placeholder(R.mipmap.img_default_meizi)
//                .error(R.mipmap.ic_load_fail)
//                .into(imageView)
    }
}