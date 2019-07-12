package com.skysoft.kotlindemo.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.skysoft.kotlindemo.R
import com.skysoft.kotlindemo.bean.HomeBeanDataX
import java.text.SimpleDateFormat
import java.util.*

class HomeAdapter(val context: Context, layoutResId: Int, data: MutableList<HomeBeanDataX>?) : BaseQuickAdapter<HomeBeanDataX, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: HomeBeanDataX?) {
        val author = helper!!.getView<TextView>(R.id.gank_item_author)
        val timer = helper.getView<TextView>(R.id.gank_item_timer)
        val desc = helper.getView<TextView>(R.id.gank_item_desc)
        val t = timeStampDate(item!!.publishTime, "yyyy-MM-dd HH:mm:ss")
        timer!!.text=t
        author!!.text= item.author
        desc.text=item.title
    }

    fun timeStampDate(time: Long, format: String?): String {
        var format = format
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss"
        }
        val sdf = SimpleDateFormat(format)
        return sdf.format(Date(time))
    }
}