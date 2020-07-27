package com.skysoft.kotlindemo.bean

data class OtherBean(
    var adExist: Boolean,
    var count: Int,
    var itemList: List<Item>,
    var nextPageUrl: String,
    var total: Int
)

data class Item(
    var `data`: Data,
    var adIndex: Int,
    var id: Int,
    var tag: Any,
    var type: String
)

data class Data(
    var ad: Boolean,
    var adTrack: List<Any>,
    var author: Author,
    var brandWebsiteInfo: Any,
    var campaign: Any,
    var category: String,
    var collected: Boolean,
    var consumption: Consumption,
    var cover: Cover,
    var dataType: String,
    var date: Long,
    var description: String,
    var descriptionEditor: String,
    var descriptionPgc: Any,
    var duration: Int,
    var favoriteAdTrack: Any,
    var id: Int,
    var idx: Int,
    var ifLimitVideo: Boolean,
    var label: Any,
    var labelList: List<Any>,
    var lastViewTime: Any,
    var library: String,
    var playInfo: List<PlayInfo>,
    var playUrl: String,
    var played: Boolean,
    var playlists: Any,
    var promotion: Any,
    var provider: Provider,
    var reallyCollected: Boolean,
    var releaseTime: Long,
    var remark: String,
    var resourceType: String,
    var searchWeight: Int,
    var shareAdTrack: Any,
    var slogan: Any,
    var src: Int,
    var subtitles: List<Any>,
    var tags: List<Tag>,
    var thumbPlayUrl: Any,
    var title: String,
    var titlePgc: Any,
    var type: String,
    var waterMarks: Any,
    var webAdTrack: Any,
    var webUrl: WebUrl
)

data class Tag(
    var actionUrl: String,
    var adTrack: Any,
    var bgPicture: String,
    var childTagIdList: Any,
    var childTagList: Any,
    var communityIndex: Int,
    var desc: Any,
    var haveReward: Boolean,
    var headerImage: String,
    var id: Int,
    var ifNewest: Boolean,
    var name: String,
    var newestEndTime: Any,
    var tagRecType: String
)

data class Consumption(
    var collectionCount: Int,
    var realCollectionCount: Int,
    var replyCount: Int,
    var shareCount: Int
)

data class PlayInfo(
    var height: Int,
    var name: String,
    var type: String,
    var url: String,
    var urlList: List<Url>,
    var width: Int
)

data class Url(
    var name: String,
    var size: Int,
    var url: String
)

data class Provider(
    var alias: String,
    var icon: String,
    var name: String
)

data class Cover(
    var blurred: String,
    var detail: String,
    var feed: String,
    var homepage: Any,
    var sharing: Any
)

data class WebUrl(
    var forWeibo: String,
    var raw: String
)

data class Author(
    var adTrack: Any,
    var approvedNotReadyVideoCount: Int,
    var description: String,
    var expert: Boolean,
    var follow: Follow,
    var icon: String,
    var id: Int,
    var ifPgc: Boolean,
    var latestReleaseTime: Long,
    var link: String,
    var name: String,
    var recSort: Int,
    var shield: Shield,
    var videoNum: Int
)

data class Shield(
    var itemId: Int,
    var itemType: String,
    var shielded: Boolean
)

data class Follow(
    var followed: Boolean,
    var itemId: Int,
    var itemType: String
)