package com.memo.business.entity.remote

data class ListEntity<T>(
    val curPage: Int = 0,
    val datas: ArrayList<T> = arrayListOf(),
    val offset: Int = 0,
    val over: Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0) {
    fun hasMore() = !over
    fun isEmpty() = datas.isEmpty()
}

const val ARTICLE_TYPE_CHAPTER = 0
const val ARTICLE_TYPE_TITLE = 1
const val ARTICLE_TYPE_PROJECT = 2
const val ARTICLE_TYPE_ARTICLE = 3

data class Article(
    val author: String = "",
    val shareUser: String = "",
    val chapterName: String = "",
    val superChapterName: String = "",
    val desc: String = "",
    val envelopePic: String = "",
    val id: Int = 0,
    val link: String = "",
    val niceShareDate: String = "",
    val projectLink: String = "",
    var title: String = "",
    val userId: Int = 0,
    val zan: Int = 0,
    val collect: Boolean = false,
    // 轮播图使用
    val url: String = "",
    val imagePath: String = "",
    // 自加属性 列表类型
    var multiItemType: Int = ARTICLE_TYPE_ARTICLE,
    // 公众号
    var chapters: ArrayList<Chapter> = arrayListOf(),
    // 自加属性 显示更多
    var showMore: Boolean = false,
    // 自加属性 最新文章列表
    var projects: ArrayList<Article> = arrayListOf()) {
    fun showAuthor() = when {
        author.isNotEmpty() -> {
            "作者：${author}"
        }
        shareUser.isNotEmpty() -> {
            "分享者：${shareUser}"
        }
        else -> {
            "匿名"
        }
    }
}

data class Chapter(val name: String = "", val id: Int = 0)
