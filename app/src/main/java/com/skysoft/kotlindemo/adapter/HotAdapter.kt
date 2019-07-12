package com.skysoft.kotlindemo.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cy.cyflowlayoutlibrary.FlowLayout
import com.cy.cyflowlayoutlibrary.FlowLayoutAdapter
import com.skysoft.kotlindemo.R
import com.skysoft.kotlindemo.bean.BelleBean
import com.skysoft.kotlindemo.bean.HotBeanData
import com.skysoft.kotlindemo.bean.HotBeanDataChildren
import com.skysoft.kotlindemo.bean.PictureBeanData

class HotAdapter(val context: Context, data: MutableList<HotBeanData>?) : BaseQuickAdapter<HotBeanData, BaseViewHolder>(R.layout.hot_recycleview_item, data) {
    override fun convert(helper: BaseViewHolder?, hotBeanData: HotBeanData?) {
        val title = helper!!.getView<TextView>(R.id.item_title)
        val flowLayout = helper.getView<FlowLayout>(R.id.flowlayout)
        val children = hotBeanData!!.children
        title!!.text = hotBeanData.name

        val flowLayoutAdapter = object : FlowLayoutAdapter<HotBeanDataChildren>(children) {
            override fun bindDataToView(holder: ViewHolder?, position: Int, bean: HotBeanDataChildren?) {
                holder!!.setText(R.id.seach_tv, bean!!.name)
            }

            override fun getItemLayoutID(position: Int, bean: HotBeanDataChildren?): Int {
                return R.layout.item_tv
            }

            override fun onItemClick(position: Int, bean: HotBeanDataChildren?) {

            }
        }
        flowLayout.setAdapter(flowLayoutAdapter)
    }
}