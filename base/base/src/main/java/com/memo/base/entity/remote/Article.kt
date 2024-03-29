package com.memo.base.entity.remote

import android.os.Parcelable
import com.chad.library.adapter.base.entity.node.BaseExpandNode
import com.chad.library.adapter.base.entity.node.BaseNode
import com.memo.base.entity.local.TodoPriority
import com.memo.base.entity.local.TodoStatus
import com.memo.base.entity.local.TodoType
import kotlinx.parcelize.Parcelize

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
    var author: String = "",
    val shareUser: String = "",
    val chapterName: String = "",
    val superChapterName: String = "",
    val desc: String = "",
    val envelopePic: String = "",
    val id: Int = 0,
    val originId: Int = 0,
    var link: String = "",
    val niceDate: String = "",
    val projectLink: String = "",
    var title: String = "",
    val userId: Int = 0,
    val zan: Int = 0,
    val collect: Boolean = false,
    // 轮播图使用
    val url: String = "",
    val imagePath: String = "",
    // 自加属性 是否置顶
    var top: Boolean = false,
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

data class HotKey(val name: String = "")

data class Category(val name: String = "", val id: Int = 0, val children: ArrayList<Chapter>)

data class Navi(val name: String = "", val articles: ArrayList<Article> = arrayListOf())

data class NodeItem(val title: String, override val childNode: MutableList<BaseNode>) : BaseExpandNode() {
    init {
        isExpanded = false
    }
}

data class NodeItemChild(val title: String, val id: Int, val link: String) : BaseNode() {
    override val childNode: MutableList<BaseNode>? = null
}

data class CoinInfo(val coinCount: Int = 0, val level: Int = 0, val rank: Int = 0, val nickname: String = "", val username: String = "")

data class CoinRecord(val coinCount: Int = 0, val desc: String = "")

data class RankRecord(val coinCount: Int = 0, val level: Int = 0, val rank: Int = 0, val username: String = "")

data class WebUrl(val id: Int = 0, val link: String = "", val name: String = "")

data class UserShareRecord(
    val coinInfo: CoinInfo = CoinInfo(), val shareArticles: ListEntity<Article> = ListEntity())

data class NotifyMessage(
    val fullLink: String = "",
    val fromUser: String = "",
    val title: String = "",
    val message: String = "",
    val niceDate: String = "",
    val tag: String = "",
)

@Parcelize
data class TodoInfo(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val dateStr: String = "",
    var completeDateStr: String = "",
    val priority: Int = 0,
    var status: Int = 0,
    val type: Int = 0) : Parcelable {

    fun getPriorityDesc(): String {
        val result = TodoPriority.values().find { it.value == priority } ?: TodoPriority.LOW
        return result.desc
    }

    fun getStatusDesc(): String {
        val result = TodoStatus.values().find { it.value == status } ?: TodoStatus.UN_COMPLETE
        return result.desc
    }

    fun getTypeDesc(): String {
        val result = TodoType.values().find { it.value == type } ?: TodoType.OTHER
        return result.desc
    }
}