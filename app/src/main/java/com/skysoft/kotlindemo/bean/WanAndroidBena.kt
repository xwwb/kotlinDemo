package com.skysoft.kotlindemo.bean

data class WanAndroidBena(
    var `data`: List<WanAndroidBeanData>,
    var errorCode: Int,
    var errorMsg: String
)

data class WanAndroidBeanData(
    var desc: String,
    var id: Int,
    var imagePath: String,
    var isVisible: Int,
    var order: Int,
    var title: String,
    var type: Int,
    var url: String
)