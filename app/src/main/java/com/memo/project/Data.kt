package com.memo.project

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-03-17 10:55
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
data class Inform(
    val list: ArrayList<InformItem> = arrayListOf(),
    val pageObject: PageObject = PageObject()
)

data class InformItem(
    val content: String = "",
    val createBy: String = "",
    val createTime: String = "",
    val creatorUserId: Any? = null,
    val fn: Int = 0,
    val fnId: Int = 0,
    val fnName: String = "",
    val id: Int = 0,
    val isRead: Int = 0,
    val projectId: Int = 0,
    val projectName: String? = null,
    val title: String = ""
)

data class PageObject(
    val pageNum: Int = 0,
    val pageSize: Int = 0,
    val totalNum: Int = 0,
    val totalPage: Int = 0
)

data class Banner(
    val list: ArrayList<BannerItem> = arrayListOf(),
    val pageObject: PageObject = PageObject()
)

data class BannerItem(
    val adjunctJson: String = "",
    val classifyId: Any? = null,
    val content: String = "",
    val contentText: String = "",
    val coverUrl: String = "",
    val createTime: String = "",
    val htmlUrl: Any? = null,
    val id: Int = 0,
    val imgUrl: String? = null,
    val isTop: String = "",
    val likeAmount: Any? = null,
    val outerLinkUrl: Any? = null,
    val title: String = "",
    val type: String = "",
    val videoUrl: Any? = null
)