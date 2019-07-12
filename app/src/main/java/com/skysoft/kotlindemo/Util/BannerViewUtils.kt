package com.skysoft.kotlindemo.Util


import com.youth.banner.Banner
import com.youth.banner.BannerConfig

/**
 * Created by Administrator on 2017\9\1 0001.
 * 无线轮播图
 */

object BannerViewUtils {
    fun setBanner(banner: Banner, data: List<String>, titleData: List<String>) {

        banner.setImageLoader(PicassoImage())
        //        // 设置图片集合
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        //设置数据
        banner.setImages(data)
        // 设置banner动画效果
        //  banner.setBannerAnimation(Transformer.ScaleInOut);
        banner.isAutoPlay(true)
        // 设置轮播时间
        banner.setDelayTime(3000)
        // banner.isAutoPlay(false);
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)// 显示数字指示器
        // 设置指示器位置（当banner模式中有指示器时）
        //   banner.setIndicatorGravity(BannerConfig.CENTER);
        // banner设置方法全部调用完毕时最后调用
        banner.setBannerTitles(titleData)
        banner.start()
    }
}
