package com.skysoft.kotlindemo.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.skysoft.kotlindemo.R
import com.skysoft.kotlindemo.bean.Item
import com.squareup.picasso.Picasso

class OtherAdapter(var context: Context, data: List<Item>) : BaseQuickAdapter<Item, BaseViewHolder>(R.layout.other_fragment_recycleview_item, data) {
    override fun convert(helper: BaseViewHolder?, item: Item?) {
        val author = helper!!.getView<TextView>(R.id.title)
        val photoView = helper.getView<ImageView>(R.id.photo_imageView)
        val desc = helper.getView<TextView>(R.id.content)
        val data = item!!.data
        val tag = item.tag

        val actionUrl = data.webUrl
        val cover = data.cover
      //  val icon = data.provider.icon
        author?.text = data.category
        desc?.text = data.description
        // Picasso.with(context).load().into(photoView)

    }


}