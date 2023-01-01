package com.memo.base.common.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.daimajia.swipe.SwipeLayout
import com.memo.base.R
import com.memo.base.entity.remote.*
import com.memo.base.utils.onItemClick
import com.memo.core.utils.ext.dp2px
import com.memo.core.utils.ext.fromHtml
import com.memo.core.utils.ext.loadRound
import com.memo.core.widget.recyclerview.StartSnapHelper

/**
 * title:通用的文章列表适配器
 * describe:
 *
 * @author memo
 * @date 2022-12-08 14:20
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
private val chapterAvatars = mapOf(
    0 to R.drawable.avatar_android,
    408 to R.drawable.chapter_hy,
    409 to R.drawable.chapter_gl,
    410 to R.drawable.chapter_ygs,
    411 to R.drawable.chapter_cxmy,
    413 to R.drawable.chapter_qyz,
    414 to R.drawable.chapter_codexs,
    415 to R.drawable.chapter_googlekfz,
    416 to R.drawable.chapter_qzs,
    417 to R.drawable.chapter_mtjstd,
    420 to R.drawable.chapter_gcssloop,
    421 to R.drawable.chapter_hlwzc,
    427 to R.drawable.chapter_susionsx,
    428 to R.drawable.chapter_cxyfy,
    434 to R.drawable.chapter_gityuan)

class ArticleAdapter(val enableSwipe: Boolean = false) : BaseProviderMultiAdapter<Article>() {

    init {
        addItemProvider(ChapterProvider())
        addItemProvider(TitleProvider())
        addItemProvider(ProjectProvider())
        addItemProvider(ArticleProvider())
        addChildClickViewIds(R.id.mItemDelete, R.id.mItemEdit, R.id.mItemArticle, R.id.mIvIcon)
    }

    /**
     * 最新项目的点击
     */
    var onProjectClick: ((Article) -> Unit)? = null

    override fun getItemType(data: List<Article>, position: Int): Int = data[position].multiItemType

}

class ChapterProvider : BaseItemProvider<Article>() {
    override val itemViewType: Int = ARTICLE_TYPE_CHAPTER
    override val layoutId: Int = R.layout.layout_item_article_chapter

    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.setImageResource(R.id.mIvChapter, chapterAvatars[item.id] ?: chapterAvatars[0]!!).setText(R.id.mTvChapter, item.title)
    }
}

class TitleProvider : BaseItemProvider<Article>() {
    override val itemViewType: Int = ARTICLE_TYPE_TITLE
    override val layoutId: Int = R.layout.layout_item_article_title

    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.setText(R.id.mTvTitle, item.title).setGone(R.id.mTvMore, !item.showMore)
    }
}

class ProjectProvider : BaseItemProvider<Article>() {
    override val itemViewType: Int = ARTICLE_TYPE_PROJECT
    override val layoutId: Int = R.layout.layout_item_article_project

    private val mAdapter = ProjectItemAdapter().apply {
        onItemClick {
            val parentAdapter = super.getAdapter()
            if (parentAdapter is ArticleAdapter) parentAdapter.onProjectClick?.invoke(it)
        }
    }

    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.getView<RecyclerView>(R.id.mRvProject).run {
            setHasFixedSize(true)
            this.onFlingListener = null
            StartSnapHelper().attachToRecyclerView(this)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            mAdapter.setList(item.projects)
            adapter = mAdapter
        }
    }

}

class ProjectItemAdapter : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.layout_item_article_project_item) {

    override fun convert(holder: BaseViewHolder, item: Article) {
        holder.run {
            setGone(R.id.mNewBg, item.envelopePic.isEmpty())
            setGone(R.id.mNewCover, item.envelopePic.isEmpty())
            if (item.envelopePic.isNotEmpty()) {
                getView<ImageView>(R.id.mNewBg).loadRound(item.envelopePic, 8.dp2px)
                getView<ImageView>(R.id.mNewCover).loadRound(item.envelopePic, 8.dp2px)
            }
            setText(R.id.mNewTitle, item.title)

            setText(
                R.id.mNewDesc, when {
                    item.desc.isNotEmpty() -> item.desc.fromHtml()
                    item.superChapterName.isNotEmpty() -> "${item.superChapterName} · ${item.chapterName}"
                    else -> item.chapterName
                })

        }
    }

}

class ArticleProvider : BaseItemProvider<Article>() {
    override val itemViewType: Int = ARTICLE_TYPE_ARTICLE
    override val layoutId: Int = R.layout.layout_item_article_item
    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.run {
            if ((getAdapter() as ArticleAdapter).enableSwipe) getView<SwipeLayout>(R.id.mSwipeLayout).close(true)

            setGone(R.id.mItemEdit, item.originId != -1)

            setGone(R.id.mLabel, !item.top)

            getView<SwipeLayout>(R.id.mSwipeLayout).isSwipeEnabled = (getAdapter() as ArticleAdapter).enableSwipe

            setImageResource(R.id.mIvIcon, chapterAvatars[item.id] ?: chapterAvatars[0]!!)

            setText(R.id.mTvName, item.showAuthor())

            setText(R.id.mTvTitle, item.title.fromHtml())

            val hasDesc = item.desc.isNotEmpty()
            setGone(R.id.mTvDesc, !hasDesc)
            setText(R.id.mTvDesc, item.desc.fromHtml())

            val hasChapter = item.superChapterName.isNotEmpty() && item.chapterName.isNotEmpty()
            setGone(R.id.mTvChapter, !hasChapter)
            setText(
                R.id.mTvChapter, if (item.superChapterName.isNotEmpty() && item.chapterName.isNotEmpty()) {
                    if ("${item.superChapterName} · ${item.chapterName}".length > 20) {
                        "${item.superChapterName}\n${item.chapterName}"
                    } else {
                        "${item.superChapterName} · ${item.chapterName}"
                    }
                } else {
                    item.chapterName
                })

            val hasPic = item.envelopePic.isNotEmpty()
            setGone(R.id.mIvPic, !hasPic)
            getView<ImageView>(R.id.mIvPic).loadRound(item.envelopePic, 5.dp2px)

            // 有标签 有描述 或者 有标签 无图片
            val showRightTime = (hasDesc || !hasPic) && hasChapter
            setGone(R.id.mTvTime, !showRightTime)
            setGone(R.id.mTvTimeDesc, showRightTime)
            setText(R.id.mTvTime, item.niceDate)
            setText(R.id.mTvTimeDesc, item.niceDate)
        }
    }
}