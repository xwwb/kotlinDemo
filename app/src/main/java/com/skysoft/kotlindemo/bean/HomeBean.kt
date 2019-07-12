package com.skysoft.kotlindemo.bean

data class HomeBean(
    var `data`: HomeBeanData,
    var errorCode: Int,
    var errorMsg: String
)

data class HomeBeanData(
    var curPage: Int,
    var datas: List<HomeBeanDataX>,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int
)

data class HomeBeanDataX(
    var apkLink: String,
    var author: String,
    var chapterId: Int,
    var chapterName: String,
    var collect: Boolean,
    var courseId: Int,
    var desc: String,
    var envelopePic: String,
    var fresh: Boolean,
    var id: Int,
    var link: String,
    var niceDate: String,
    var origin: String,
    var prefix: String,
    var projectLink: String,
    var publishTime: Long,
    var superChapterId: Int,
    var superChapterName: String,
    var tags: List<Any>,
    var title: String,
    var type: Int,
    var userId: Int,
    var visible: Int,
    var zan: Int
)