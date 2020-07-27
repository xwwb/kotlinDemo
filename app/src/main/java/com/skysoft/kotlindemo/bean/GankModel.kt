package com.skysoft.kotlindemo.bean
data class GankModel(
    var data: List<GankModelData>,
    var page: Int,
    var page_count: Int,
    var status: Int,
    var total_counts: Int
)

data class GankModelData(
    var _id: String,
    var author: String,
    var category: String,
    var createdAt: String,
    var desc: String,
    var images: List<String>,
    var likeCounts: Int,
    var publishedAt: String,
    var stars: Int,
    var title: String,
    var type: String,
    var url: String,
    var views: Int
)