package com.skysoft.kotlindemo.bean

data class BelleBean(
    var error: Boolean,
    var results: List<Result>
)

data class Result(
    var _id: String,
    var createdAt: String,
    var desc: String,
    var publishedAt: String,
    var source: String,
    var type: String,
    var url: String,
    var used: Boolean,
    var who: String
)