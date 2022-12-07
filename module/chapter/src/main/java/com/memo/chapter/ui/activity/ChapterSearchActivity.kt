package com.memo.chapter.ui.activity

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.memo.business.base.BaseVmActivity
import com.memo.business.common.adapter.ArticleAdapter
import com.memo.business.entity.remote.Article
import com.memo.business.entity.remote.ListEntity
import com.memo.business.utils.finish
import com.memo.business.utils.showEmpty
import com.memo.chapter.databinding.ActivityChapterSearchBinding
import com.memo.chapter.viewmodel.ChapterViewModel
import com.memo.core.utils.ext.startActivity

/**
 * title:公众号文章搜索
 * describe:
 *
 * @author memo
 * @date 2022-12-09 16:45
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ChapterSearchActivity : BaseVmActivity<ChapterViewModel, ActivityChapterSearchBinding>() {

    companion object {
        fun start(context: Context, chapterId: Int, title: String) {
            context.startActivity<ChapterSearchActivity>("cid" to chapterId, "title" to title)
        }
    }

    private var cid: Int = -1

    private var title: String = "搜索"

    private var pageNum: Int = 1

    private val mAdapter = ArticleAdapter()

    override fun showContent(): Boolean = true

    /*** 初始化数据 ***/
    override fun initData() {
        cid = intent.getIntExtra("cid", cid)
        title = intent.getStringExtra("title") ?: title
    }

    /*** 初始化控件 ***/
    override fun initView() {
        mBinding.run {
            mTitleBar.setTitle(title)
            mRvList.run {
                layoutManager = LinearLayoutManager(mContext)
                adapter = mAdapter
            }
        }
    }

    /*** 初始化监听 ***/
    override fun initListener() {
        mBinding.run {
            mSearchBar.setOnSearchListener {
                showLoading()
                pageNum = 1
                mViewModel.getChapterArticle(cid, pageNum, it)
            }
            mRefreshLayout.setOnRefreshListener {
                pageNum = 1
                mViewModel.getChapterArticle(cid, pageNum, mSearchBar.getValue())
            }
            mRefreshLayout.setOnLoadMoreListener {
                mViewModel.getChapterArticle(cid, pageNum, mSearchBar.getValue())
            }
        }

        mViewModel.articleLiveData.observe(this, this::onArticleResponse)
    }

    /*** 页面开始请求 ***/
    override fun start() {
    }

    /**
     * 公众号文章返回
     * @param data ListEntity<Article>
     */
    private fun onArticleResponse(data: ListEntity<Article>) {
        mAdapter.showEmpty(data.isEmpty())
        if (data.curPage == 1) mAdapter.setList(data.datas) else mAdapter.addData(data.datas)
        pageNum = data.curPage + 1
        mBinding.mRefreshLayout.finish(data.hasMore())
    }
}