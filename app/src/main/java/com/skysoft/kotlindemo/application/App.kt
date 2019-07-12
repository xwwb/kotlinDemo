package com.skysoft.kotlindemo.application

import android.app.Application
import com.ganxin.library.LoadDataLayout
import com.skysoft.kotlindemo.R
import kotlinx.android.synthetic.main.smartrefresh_layout.view.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        LoadDataLayout.getBuilder()
                .setLoadingViewLayoutId(R.layout.loading_layout)//修改Loading布局
                //.setEmptyImgId(R.mipmap.empyt)//设置空视图图片
                //.setErrorImgId(R.mipmap.error)//设置请求错误图片
               // .setNoNetWorkImgId(R.mipmap.error)//设置网络请求错误图片
              //  .setEmptyImageVisible(true)//设置空视图显示
               // .setErrorImageVisible(true)//设置错误视图显示
              //  .setNoNetWorkImageVisible(true)//设置网络请求错误视图显示
                .setEmptyText("暂无内容")//空视图下字体描述
                .setErrorText("网络连接发生问题，请检查网络设置")//请求失败视图下文字描述
                .setNoNetWorkText("网络连接发生问题，请检查网络设置")//网络问题下文字描述
                .setAllTipTextSize(17)//设置文字字体大小
                .setAllTipTextColor(R.color.title_layout)//设置文字字体颜色
                .setAllPageBackgroundColor(R.color.white)//设置loading页面背景颜色
//                .setReloadBtnText("重新加载")
//                .setReloadBtnTextSize(17)
//                .setReloadBtnTextColor(R.color.colorPrimary)
//                .setReloadBtnBackgroundResource(R.drawable.selector_btn_normal)
//                .setReloadBtnVisible(true)
//                .setReloadClickArea(LoadDataLayout.FULL);
    }
}