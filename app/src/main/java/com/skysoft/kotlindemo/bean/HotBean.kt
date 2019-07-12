package com.skysoft.kotlindemo.bean

data class HotBean(
    var `data`: List<HotBeanData>,
    var errorCode: Int,
    var errorMsg: String
)

data class HotBeanData(
    var children: List<HotBeanDataChildren>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)

data class HotBeanDataChildren(
    var children: List<Any>,
    var courseId: Int,
    var id: Int,
    var name: String,
    var order: Int,
    var parentChapterId: Int,
    var userControlSetTop: Boolean,
    var visible: Int
)